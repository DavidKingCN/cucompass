/*
 *    功能名称   ： httpclient 封裝实现1.2
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.http.core;
import java.util.Map;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
// TODO: Auto-generated Javadoc

/**
 * 普通的http请求模板.
 *
 * @author daikai
 */
public final class HttpTemplate {

	/** The http req. */
	static HttpReq  httpReq = null;
	
	/**
	 * Do post.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String doPost(String url,Object...params) {
		httpReq = new HttpReqImpl(url,HttpPost.METHOD_NAME);
		return httpReq.execReq(params);
	}

	/**
	 * Do post.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String doPost(String url,Map<String,String> params) {
		return doPost(url, null,params,null);
	}

	/**
	 * Do post.
	 *
	 * @param url the url
	 * @param json the json
	 * @return the string
	 */
	public static String doPost(String url,String json) {
		return doPost(url, null,json,null);
	}
	
	
	/**
	 * Do put.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String doPut(String url,Object...params) {
		httpReq = new HttpReqImpl(url,HttpPut.METHOD_NAME);
		return httpReq.execReq(params);
	}

	/**
	 * Do get.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String doGet(String url,Object...params) {
		httpReq = new HttpReqImpl(url,HttpGet.METHOD_NAME);
		return httpReq.execReq(params);
	}
	
	/**
	 * Do get.
	 *
	 * @param url the url
	 * @return the string
	 */
	public static String doGet(String url) {
		return doGet(url, null,null,null);
	}
	//参数是键值对的形式
	/**
	 * Do get.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String doGet(String url,Map<String,String> params) {
		return doGet(url, null,params,null);
	}

	/**
	 * Do get.
	 *
	 * @param url the url
	 * @param json the json
	 * @return the string
	 */
	public static String doGet(String url,String json) {
		return doGet(url, null,json,null);
	}
	
	/**
	 * Do delete.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String doDelete(String url,Object...params) {
		httpReq = new HttpReqImpl(url,HttpDelete.METHOD_NAME);
		return httpReq.execReq(params);
	}

	/**
	 * Do trace.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String doTrace(String url,Object...params){
		httpReq = new HttpReqImpl(url,HttpTrace.METHOD_NAME);
		return httpReq.execReq(params);
	}
	
	/**
	 * Do options.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String doOptions(String url,Object...params){
		httpReq = new HttpReqImpl(url,HttpOptions.METHOD_NAME);
		return httpReq.execReq(params);
	}
}
