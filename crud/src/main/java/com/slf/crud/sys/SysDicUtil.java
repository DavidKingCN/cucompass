package com.slf.crud.sys;

import java.util.LinkedHashMap;

public class SysDicUtil {

	private static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> sysDic;

	private SysDicUtil() {
	}

	public static void setSysDic(
			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> aSysDic) {
		sysDic = aSysDic;
	}
	
	public static LinkedHashMap<String,LinkedHashMap<String, String>> getAllFields(String tableName){
		LinkedHashMap<String,LinkedHashMap<String, String>> allTableFields = null;
		try {
			allTableFields = new LinkedHashMap<>();
			if (sysDic.containsKey(tableName)) {
				allTableFields = sysDic.get(tableName);
			}
		} catch (Exception e) {
			
		}
		return allTableFields;
	}
	/**
	 * 获取某一数据字典所有 值-描述
	 * 
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	public static LinkedHashMap<String, String> getAllFields(String tableName,
			String fieldName) {

		if (sysDic.containsKey(tableName)) {
			LinkedHashMap<String, LinkedHashMap<String, String>> subDic = sysDic
					.get(tableName);

			if (subDic.containsKey(fieldName)) {
				return subDic.get(fieldName);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取某个数据字典某个域 值对应的描述
	 * 
	 * @param tableName
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static String getFieldDesc(String tableName, String fieldName,
			String value) {
		if (sysDic.containsKey(tableName)) {
			if (sysDic.get(tableName).containsKey(fieldName)) {
				return sysDic.get(tableName).get(fieldName).get(value);
			} else {
				return value;
			}

		} else {
			return value;
		}
	}

}
