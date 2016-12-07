/*
 *    功能名称   ： xpath数据提取实现1.1
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.html.parse;



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
		int stIdx = pickAgent.getPath().indexOf("<");int ndIdx = pickAgent.getPath().lastIndexOf(">");
		String realPath = pickAgent.getPath().substring(0, stIdx); String limitRule = pickAgent.getPath().substring(stIdx+1, ndIdx);
		int needIdx = 0;String regExp = null;int groupNo = 0;
		if(limitRule.matches("\\d+,.+")){
			int commaIdx = limitRule.indexOf(","); String indxStr = limitRule.substring(0,commaIdx); needIdx = Integer.parseInt(indxStr);
			regExp = limitRule.substring(commaIdx+1); if(regExp.contains("(")&&regExp.contains(")"))	groupNo = 1;
		}else needIdx = Integer.parseInt(limitRule);
		String needParseStr = null;
		try { needParseStr = XPathUtils.pathValByIdx(pickAgent.getNode(), realPath,needIdx, XPathUtils.isAttr(realPath));} catch (XPatherException ignore) {}
		String val = null;
		if(needParseStr!=null&&!needParseStr.equals("")){
			val = needParseStr;pickAgent.setObeyRule(true);	if(regExp!=null && !regExp.equals("")) val = MatchUtils.getOnlyMatchs(val, regExp, groupNo);
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
