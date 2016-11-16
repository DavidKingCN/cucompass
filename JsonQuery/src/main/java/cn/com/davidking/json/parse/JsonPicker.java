/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse;
// TODO: Auto-generated Javadoc

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Interface JsonPicker.
 */
public interface JsonPicker {
	
	/**
	 * Pick.
	 */
	void pick();
	
	
	/**
	 * Inits the.
	 *
	 * @param kvs the kvs
	 */
	void init(Map<String,Object> kvs);
}
