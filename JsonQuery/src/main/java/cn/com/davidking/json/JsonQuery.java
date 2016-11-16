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
import java.util.Map;

import cn.com.davidking.json.parse.JsonParser;


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
		String single = null;
		try {
			Map<String,Object> result = new JsonParser().jsonPath(json, path);
			if (result != null)
				single = result.get(Constant.SINGLE_VALUE_KEY).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return single;

	}

	/**
	 * Gets the list val.
	 *
	 * @param json the json
	 * @param path the path
	 * @return the list val
	 */
	public static List<String> getListVal(String json, String path) {
		List<String> resultList = null;
		try {
			Map<String,Object> result = new JsonParser().jsonPath(json, path);
			if (result != null)
				resultList= (List<String>) result.get(Constant.LIST_VALUE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;

	}

	/**
	 * Gets the map val.
	 *
	 * @param json the json
	 * @param path the path
	 * @return the map val
	 */
	public static List<Map<String, String>> getMapVal(String json, String path) {
		
		List<Map<String, String>> ms = null;
		try {
			Map<String,Object> result = new JsonParser().jsonPath(json, path);
			if (result != null)
				ms =  (List<Map<String, String>>) result.get(Constant.MAP_VALUE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ms;
	}
}
