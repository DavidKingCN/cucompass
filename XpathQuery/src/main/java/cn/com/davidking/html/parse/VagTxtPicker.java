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
 * The Class VagTxtPicker.
 */
public class VagTxtPicker implements DataPicker {
	
	/** The pick agent. */
	PickAgent pickAgent;
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.html.parse.DataPicker#get()
	 */
	@Override
	public String get() {
		int stIdx = pickAgent.getPath().lastIndexOf("["); 
		int ndIdx = pickAgent.getPath().lastIndexOf("]");
		String realPath = pickAgent.getPath().substring(0, stIdx); 
		String vagRule = pickAgent.getPath().substring(stIdx+1, ndIdx); 
		int groupNo = 0; 
		if(vagRule.contains("(")&&vagRule.contains(")")) 
			groupNo = 1; 
		boolean isAttr = XPathUtils.isAttr(realPath); 
		String val=null;
		try { 
			val = XPathUtils.pathVal(pickAgent.getNode(), realPath, isAttr); 
		} catch (XPatherException ignore) {}
		String result =null;
		if(val!=null && !val.equals("")){ 
			pickAgent.setObeyRule(true); 
			result = MatchUtils.getOnlyMatchs(val, vagRule, groupNo);
		}
		return result.replaceAll("\\s+", " ");
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.html.parse.DataPicker#init(cn.com.davidking.html.parse.PickAgent)
	 */
	@Override
	public void init(PickAgent pickAgent) {
		this.pickAgent = pickAgent;
	}

}
