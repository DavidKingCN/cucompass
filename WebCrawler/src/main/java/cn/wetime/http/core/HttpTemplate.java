/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.http.core;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;

/**
 * http模板方法
 * 
 * @author daikai
 * @version 1.0
 * @note 使用说明 List<Header> 放入第一位置  Map<key,value>放入第二个位置
 */
public final class HttpTemplate {

	static WtRequest  wtRequest = null;
	
	public static String doPost(String url,Object...params) {
		wtRequest = new ExeRequest(url,HttpPost.METHOD_NAME);
		return wtRequest.exeRequest(params);
	}

	public static String doPut(String url,Object...params) {
		wtRequest = new ExeRequest(url,HttpPut.METHOD_NAME);
		return wtRequest.exeRequest(params);
	}

	public static String doGet(String url,Object...params) {
		wtRequest = new ExeRequest(url,HttpGet.METHOD_NAME);
		return wtRequest.exeRequest(params);
	}

	public static String doDelete(String url,Object...params) {
		wtRequest = new ExeRequest(url,HttpDelete.METHOD_NAME);
		return wtRequest.exeRequest(params);
	}

	public static String doTrace(String url,Object...params){
		wtRequest = new ExeRequest(url,HttpTrace.METHOD_NAME);
		return wtRequest.exeRequest(params);
	}
	
	public static String doOptions(String url,Object...params){
		wtRequest = new ExeRequest(url,HttpOptions.METHOD_NAME);
		return wtRequest.exeRequest(params);
	}
}
