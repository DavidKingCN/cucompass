/*
 *    功能名称   ： xpath数据提取实现1.1
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.html.parse;

// TODO: Auto-generated Javadoc
/**
 * The Interface DataPicker.
 */
public interface DataPicker {
	
	/**
	 * Gets the.
	 *
	 * @return the string
	 */
	public String get();
	
	/**
	 * Inits the.
	 *
	 * @param fetchAgent the fetch agent
	 */
	void init(PickAgent fetchAgent);
	
}
