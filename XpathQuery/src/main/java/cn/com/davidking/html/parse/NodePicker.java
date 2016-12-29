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

// TODO: Auto-generated Javadoc
/**
 * The Class NodePicker.
 */
public class NodePicker implements DataPicker {
	
	/** The pick agent. */
	private PickAgent pickAgent;
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.html.parse.DataPicker#get()
	 */
	@Override
	public String get() {
		String result = null; 
		String needRt = null;
		try { 
			needRt = XPathUtils.pathVal(pickAgent.getNode(), pickAgent.getPath(), false); 
		} catch (XPatherException ignore) {}
		if(needRt!=null&&!needRt.equals("")){ 
			result = needRt;
			pickAgent.setObeyRule(true); 
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
