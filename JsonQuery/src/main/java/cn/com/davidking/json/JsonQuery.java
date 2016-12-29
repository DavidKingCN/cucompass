/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.davidking.JsonParserBuilder;


// TODO: Auto-generated Javadoc
/**
 * The Class JsonQuery.
 */
@SuppressWarnings("all")
public final class JsonQuery {
	
	/**
	 * Gets the single val.
	 *
	 * @param json the json
	 * @param path the path
	 * @return the single val
	 */
	public static String getSingleVal(String json, String path) {
		return getResult(json, path).get(Constant.SINGLE_VALUE_KEY).toString();
	}

	/**
	 * Gets the list val.
	 *
	 * @param json the json
	 * @param path the path
	 * @return the list val
	 */
	public static List<String> getListVal(String json, String path) {
		return (List<String>) getResult(json, path).get(Constant.LIST_VALUE_KEY);
	}

	/**
	 * Gets the map val.
	 *
	 * @param json the json
	 * @param path the path
	 * @return the map val
	 */
	public static List<Map<String, String>> getMapVal(String json, String path) {
		return (List<Map<String, String>>) getResult(json, path).get(Constant.MAP_VALUE_KEY);
	}
	
	public static Map<String,Object> getResult(String json,String path){
		Map<String,Object> initVal = new HashMap<>();
		initVal.put(Constant.SINGLE_VALUE_KEY, "");
		initVal.put(Constant.LIST_VALUE_KEY, null);
		initVal.put(Constant.MAP_VALUE_KEY, null);
		Map<String, Object> rt=JsonParserBuilder.newJsonParser().jsonPath(json, path);
		return rt==null?initVal:rt;
	}
}
