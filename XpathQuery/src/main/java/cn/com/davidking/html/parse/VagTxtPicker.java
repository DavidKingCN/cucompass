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
		String path = pickAgent.getPath();
		int stIdx = path.lastIndexOf("[");
		int ndIdx = path.lastIndexOf("]");
		String realPath = path.substring(0, stIdx);
		String vagRule = path.substring(stIdx+1, ndIdx);
		vagRule = vagRule.substring(stIdx, ndIdx);
		
		boolean isAttr = XPathUtils.isAttr(realPath);
		TagNode node = pickAgent.getNode();
		String val=null;
		try {
			val = XPathUtils.pathVal(node, realPath, isAttr);
		} catch (XPatherException e) {
			return null;
		}
		
		String result =null;
		if(val!=null && !val.equals("")){
			pickAgent.setObeyRule(true);
			result = MatchUtils.getOnlyMatchs(val, vagRule, 1);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.html.parse.DataPicker#init(cn.com.davidking.html.parse.PickAgent)
	 */
	@Override
	public void init(PickAgent pickAgent) {
		this.pickAgent = pickAgent;
	}

}
