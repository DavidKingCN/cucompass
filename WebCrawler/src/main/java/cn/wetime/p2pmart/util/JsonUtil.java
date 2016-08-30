/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.util;

import flexjson.JSONSerializer;

public class JsonUtil {
	public static final String CLASS_FINAL = "*.class";
	/**
	 * 对象序列化json字串，不包含.class项
	 * @param o
	 * @return
	 */
	public static String serialOffClass(Object o) {
		return new JSONSerializer().exclude(JsonUtil.CLASS_FINAL).serialize(o);
	}
	
	public static String deepSerialOffClass(Object o) {
		return new JSONSerializer().exclude(JsonUtil.CLASS_FINAL).deepSerialize(o);
	}
}
