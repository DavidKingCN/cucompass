/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking;

import cn.com.davidking.json.JsonToolKit;
// TODO: Auto-generated Javadoc
import cn.com.davidking.json.parse.JsonParser;

/**
 * The Class JsonToolKitBuilder.
 *
 * @author daikai
 */
public final class JsonParserBuilder {
	
	/**
	 * New json tool kit.
	 *
	 * @return the json tool kit
	 */
	public static JsonToolKit newJsonToolKit(){ 
		return new JsonToolKit();
	}
	
	public static JsonParser newJsonParser(){
		return new JsonParser();
	}
}
