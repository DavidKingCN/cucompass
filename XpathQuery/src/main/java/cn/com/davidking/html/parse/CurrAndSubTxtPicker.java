package cn.com.davidking.html.parse;

import java.util.List;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class CurrAndSubTxtPicker implements DataPicker {
	
	/** The pick agent. */
	PickAgent pickAgent;

	@Override
	public String get() {
		String matchPath = pickAgent.getPath();
		String[] rules = matchPath.split(".text\\(\\)");
		boolean existSub = rules.length > 1 ;
		String realPath = rules[0];
		String subPath = "";
		
		List<TagNode> nodes = XPathUtils.mutilNodes(pickAgent.getNode(), realPath);
		TagNode targetNode = nodes.get(0);
		String result = "";
//		String subVal = "";
		
		StringBuffer subBuf = new StringBuffer(); 
		int repeatTm = rules.length -1;
		for(int j=repeatTm;j>=1;j--)
		if(existSub){
			subPath = rules[j];
			
			List<TagNode> snodes = XPathUtils.mutilNodes(targetNode, subPath);
			if(!snodes.isEmpty()){
				TagNode stargetNode = snodes.get(0);
				List<TagNode> ssubs = stargetNode.getChildTagList();
				if(!ssubs.isEmpty()){
					for(TagNode ssub:ssubs)
						ssub.removeFromTree();
				}
				try {
					subBuf.append(stargetNode.getText().toString()+ " ");
				} catch (Exception e) { }
			}
		}
		List<TagNode> subs = targetNode.getChildTagList();
		if(!subs.isEmpty()){
			for(TagNode sub:subs)
				sub.removeFromTree();
		}
		result = targetNode.getText().toString();
		if(result!=null && !result.equals("")){
			pickAgent.setObeyRule(true);
			result = result.replaceAll("\\s+", "") +" "+ subBuf.toString().replaceAll("\\s+", " ");
		}
		
		return result;
	}

	@Override
	public void init(PickAgent fetchAgent) {
		this.pickAgent = fetchAgent;
	}

}
