package cn.com.davidking.json.parse;

import java.util.List;
import java.util.Map;

import cn.com.davidking.json.Constant;


public class JsonQuery {
	public static String getSingleVal(String json, String path) {
		Map<String,Object> result = new JsonParser().jsonPath(json, path);
		if (result != null)
			return result.get(Constant.SINGLE_VALUE_KEY).toString();
		return null;

	}

	/**
	 * 
	 * @param json
	 * @param path
	 * @return
	 */
	public static List<String> getListVal(String json, String path) {
		
		Map<String,Object> result = new JsonParser().jsonPath(json, path);
		if (result != null)
			return (List<String>) result.get(Constant.LIST_VALUE_KEY);

		return null;

	}

	public static List<Map<String, String>> getMapVal(String json, String path) {
		Map<String,Object> result = new JsonParser().jsonPath(json, path);
		if (result != null)
			return (List<Map<String, String>>) result.get(Constant.MAP_VALUE_KEY);
		return null;
	}
}
