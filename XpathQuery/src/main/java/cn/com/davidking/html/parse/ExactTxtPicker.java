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


// TODO: Auto-generated Javadoc
/**
 * The Class ExactTxtPicker.
 */
public class ExactTxtPicker implements DataPicker {
	
	/** The pick agent. */
	PickAgent pickAgent;
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.html.parse.DataPicker#get()
	 */
	@Override
	public String get() {
		String path = pickAgent.getPath();
		int stPos = path.indexOf("(");
		int ndPos = path.indexOf(")");
		String exactWords = path.substring(stPos+1, ndPos);
		String realPath = path.substring(0,stPos);
		boolean isAttr = XPathUtils.isAttr(realPath);
		TagNode node = pickAgent.getNode();
		String val=null;
		try {
			val = XPathUtils.pathVal(node, realPath, isAttr);
		} catch (XPatherException ignore) {}
		
		String result = "";
		if(val.contains(exactWords)){
			pickAgent.setObeyRule(true);
			result = val;
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
