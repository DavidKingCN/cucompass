/*
 *    功能名称   ： xpath数据提取实现1.1
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.html.parse;

import java.util.List;

import org.htmlcleaner.TagNode;

import cn.com.davidking.util.MatchUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class HtmlPicker.
 */
public class HtmlPicker implements DataPicker {
	
	/** The pick agent. */
	PickAgent pickAgent;
	
	/**
	 * The Constructor.
	 */
	public HtmlPicker() {
		super();
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.html.parse.DataPicker#init(cn.com.davidking.html.parse.PickAgent)
	 */
	public void init(PickAgent pickAgent) {
		this.pickAgent = pickAgent;
	}


	/* (non-Javadoc)
	 * @see cn.com.davidking.html.parse.DataPicker#get()
	 */
	@Override
	public String get() {
		int stIdx = pickAgent.getPath().indexOf("{"); int ndIdx = pickAgent.getPath().indexOf("}");
		String realPath = pickAgent.getPath().substring(0, stIdx); String limitRule = pickAgent.getPath().substring(stIdx+1, ndIdx);
		int needIdx = 0; String regExp = null;
		if(limitRule.matches("\\d,html\\[.+\\]")){
			int commaIdx = limitRule.indexOf(","); String indxStr = limitRule.substring(0,commaIdx); needIdx = Integer.parseInt(indxStr);
			int rdIdx = limitRule.indexOf("[");	int thIdx = limitRule.lastIndexOf("]");	regExp = limitRule.substring(rdIdx+1, thIdx);
		}else if(limitRule.matches("\\d,html")){
			int commaIdx = limitRule.indexOf(","); String indxStr = limitRule.substring(0,commaIdx); needIdx = Integer.parseInt(indxStr);
		}
		List<TagNode>nodes = XPathUtils.mutilNodes(pickAgent.getNode(), realPath); TagNode needNode = nodes.get(needIdx);
		String html = null;	String htmlRt = pickAgent.getCleaner().getInnerHtml(needNode);
		if(htmlRt!=null&&!htmlRt.equals("")){
			html = htmlRt;pickAgent.setObeyRule(true); if(regExp!=null && !regExp.equals("")) html = MatchUtils.getOnlyMatchs(html, regExp, 1);
		}
		return html;
	}

}
