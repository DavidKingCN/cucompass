/*
 *    功能名称   ： xpath数据提取实现1.1
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.html.parse;



import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import cn.com.davidking.util.MatchUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ArrSubPicker.
 */
public class ArrSubPicker implements DataPicker {
	
	/** The pick agent. */
	PickAgent pickAgent;
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.html.parse.DataPicker#get()
	 */
	@Override
	public String get() {
		String path = pickAgent.getPath();
		
		int stIdx = path.indexOf("<");
		int ndIdx = path.lastIndexOf(">");

		String realPath = path.substring(0, stIdx);
		String limitRule = path.substring(stIdx+1, ndIdx);
		
		int needIdx = 0;
		String regExp = null;
		int groupNo = 0;
		
		if(limitRule.matches("\\d+,.+")){
			int commaIdx = limitRule.indexOf(",");
			String indxStr = limitRule.substring(0,commaIdx);
			needIdx = Integer.parseInt(indxStr);
			
			regExp = limitRule.substring(commaIdx+1);
			if(regExp.contains("(")&&regExp.contains(")"))
				groupNo = 1;
		}else{
			needIdx = Integer.parseInt(limitRule);
		}
		
		TagNode node = pickAgent.getNode();
		
		String needParseStr = null;
		try {
			needParseStr = XPathUtils.pathValByIdx(node, realPath,needIdx, XPathUtils.isAttr(realPath));
		} catch (XPatherException ignore) {}
		
		String val = null;
		if(needParseStr!=null&&!needParseStr.equals("")){
			val = needParseStr;
			if(regExp!=null && !regExp.equals("")){
				val = MatchUtils.getOnlyMatchs(val, regExp, groupNo);
			}
			pickAgent.setObeyRule(true);
		}
		
		return val;

	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.html.parse.DataPicker#init(cn.com.davidking.html.parse.PickAgent)
	 */
	@Override
	public void init(PickAgent pickAgent) {
		this.pickAgent = pickAgent;
	}

}
