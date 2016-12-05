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

import org.htmlcleaner.HtmlCleaner;
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
		String path = pickAgent.getPath();
		
		
		int stIdx = path.indexOf("{");
		int ndIdx = path.indexOf("}");

		String realPath = path.substring(0, stIdx);
		String limitRule = path.substring(stIdx+1, ndIdx);
		
		int needIdx = 0;
		String regExp = null;
		
		if(limitRule.matches("\\d,html\\[.+\\]")){
			int commaIdx = limitRule.indexOf(",");
			String indxStr = limitRule.substring(0,commaIdx);
			needIdx = Integer.parseInt(indxStr);
			
			int rdIdx = limitRule.indexOf("[");
			int thIdx = limitRule.lastIndexOf("]");
			regExp = limitRule.substring(rdIdx+1, thIdx);
		}else if(limitRule.matches("\\d,html")){
			int commaIdx = limitRule.indexOf(",");
			String indxStr = limitRule.substring(0,commaIdx);
			needIdx = Integer.parseInt(indxStr);
		}
		
		
		HtmlCleaner hc = pickAgent.getCleaner();
		TagNode node = pickAgent.getNode();
		
		List<TagNode>nodes = XPathUtils.mutilNodes(node, realPath);
		TagNode needNode = nodes.get(needIdx);
		String html = null;
		String htmlRt = hc.getInnerHtml(needNode);
		if(htmlRt!=null&&!htmlRt.equals("")){
			html = htmlRt;
			if(regExp!=null && !regExp.equals(""))
				html = MatchUtils.getOnlyMatchs(html, regExp, 1);
			pickAgent.setObeyRule(true);
		}
		
		return html;
	}

	//演出推荐指数：<img src="/images/star1.gif" width="55" height="12" alt="" />
	///images/star(\\d+).gif
	public static void main(String[] args) {
		String s = "演出推荐指数：<img src=\"/images/star1.gif\" width=\"55\" height=\"12\" alt=\"\" />";
		String regExp = "演出推荐指数：.+/images/star(\\d+).gif.+";
		String rt = MatchUtils.getOnlyMatchs(s, regExp, 1);
		System.out.println(rt);
	}
}
