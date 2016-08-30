/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.config;

import java.util.HashMap;
import java.util.Map;


public final class Cache {
	private static Cache cache = new Cache();
	
	private Cache(){}
	
	public static Cache newInstance(){
		if(cache==null)
			cache = new Cache();
		return cache;
	}
	
	private static Map<String,String> globalCache = new HashMap<String,String>();
	
	
	public void set(String key,String value){
		globalCache.put(key, value);
	}
	
	public String get(String key){
		String value = null;
		if(globalCache.containsKey(key))
			value = globalCache.get(key);
		return value;
	}
	
}
