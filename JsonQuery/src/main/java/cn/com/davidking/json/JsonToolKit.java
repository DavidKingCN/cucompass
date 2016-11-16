/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json;

import java.util.List;

import cn.com.davidking.json.parse.JsonPickTools;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonToolKit.
 */
public class JsonToolKit extends JsonPickTools {
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPickTools#getElementsByJsonArr(java.lang.String)
	 */
	public  List<String> getElementsByJsonArr(String jsonArr){
		return super.getElementsByJsonArr(jsonArr);
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPickTools#getElementsByJsonArr(java.lang.String, java.lang.String)
	 */
	public  List<String> getElementsByJsonArr(String json,String key){
		return super.getElementsByJsonArr(json, key);
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPickTools#getClosureJson(java.lang.String, java.lang.String)
	 */
	public  String getClosureJson(String json,String key){
		return super.getClosureJson(json, key);
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPickTools#getClosureJson(java.lang.String, java.lang.String, boolean)
	 */
	public  String getClosureJson(String json,String key,boolean needPrefix){
		return super.getClosureJson(json, key, needPrefix);
	}
}
