package cn.com.davidking.html.parse;

import java.util.List;

import org.htmlcleaner.HtmlCleaner;
//import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import cn.com.davidking.util.MatchUtils;

public class TxtTrimPicker implements DataPicker {
	/** The pick agent. */
	private PickAgent pickAgent;

	@Override
	public String get() {
		String regPath = pickAgent.getPath();
		boolean containTextFunc = regPath.contains(".text()");
		boolean containTrimFunc = regPath.contains(".trim()");
		String realPath = regPath;
		realPath = containTextFunc?realPath.replace(".text()", ""):realPath;
		realPath = containTrimFunc?realPath.replace(".trim()", ""):realPath;
		//TagNode htmNode = pickAgent.getNode();
		HtmlCleaner hc = pickAgent.getCleaner();
		List<TagNode>nodes = XPathUtils.mutilNodes(pickAgent.getNode(), realPath);
		TagNode targetNode = nodes.get(0);
		String htm = hc.getInnerHtml(targetNode);
		String result = null;
		try {
			result = MatchUtils.replaceHtmTag(htm, " ");//XPathUtils.recurCall(targetNode,containTrimFunc);
			
			result = result.replaceAll("\\s+", " ");
			
		} catch (Exception ignore) { }
		
		if(result!=null && !result.equals("")){
			pickAgent.setObeyRule(true); 
		}
		
		return result;
	}

	@Override
	public void init(PickAgent pickAgent) {
		this.pickAgent = pickAgent;
	}

}
