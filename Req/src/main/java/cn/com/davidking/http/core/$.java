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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
// TODO: Auto-generated Javadoc

/**
 * The Class AsynTemplate.
 *
 * @author 异步请求模板
 */
public final class $ {

	/** The asyn req. */
	static HttpReq  asynReq = null;
	
	/** The need totals. */
	static int needTotals = 0;
	
	
	public static void setExecutor(ExecutorService exeService){
		AsynReqImpl.setExeService(exeService);
	}
	
	public static void setCharset(String charset){
		AsynReqImpl.setCharset(charset);
	}
	
	public static void closeExecutor(){
		ExecutorService exeService = AsynReqImpl.getExeService();
		if(exeService!=null)
			new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
	}
	/**
	 * Do post.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param params the params
	 * @return the string
	 */
	public static String post(String url,boolean shutdown,Object...params) {
		asynReq = new AsynReqImpl(url,shutdown,HttpPost.METHOD_NAME);
		return asynReq.execReq(params);
	}

	
	/**
	 * Do post.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param params the params
	 * @return the string
	 */
	public static String post(String url,boolean shutdown,Map<String,String> params) {
		return post(url,shutdown, null,params,null);
	}
	
	/**
	 * Do post.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String post(String url,Map<String,String> params) {
		return post(url,true, null,params,null);
	}

	/**
	 * Do post.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param json the json
	 * @return the string
	 */
	public static String post(String url,boolean shutdown,String json) {
		return post(url,shutdown, null,json,null);
	}
	/**
	 * Do post.
	 *
	 * @param url the url
	 * @param json the json
	 * @return the string
	 */
	public static String post(String url,String json) {
		return post(url,true, null,json,null);
	}
	
	/**
	 * Do put.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param params the params
	 * @return the string
	 */
	public static String doPut(String url,boolean shutdown,Object...params) {
		asynReq = new AsynReqImpl(url,shutdown,HttpPut.METHOD_NAME);
		return asynReq.execReq(params);
	}

	/**
	 * Do get.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param params the params
	 * @return the string
	 */
	public static String get(String url,boolean shutdown,Object...params) {
		asynReq = new AsynReqImpl(url,shutdown,HttpGet.METHOD_NAME);
		return asynReq.execReq(params);
	}
	
	public static String get(String url,Callback callback,Object...params) {
		$.setExecutor(Executors.newFixedThreadPool(4));
		asynReq = new AsynReqImpl(url,HttpGet.METHOD_NAME);
		String result = asynReq.execReq(params);
		callback.closeService();
		return result;
	}
	
	public static String get(String url,Callback callback) {
		
		return get(url,callback,null,null,null);
	}
	/**
	 * Do get.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String doGetNotStop(String url,Object...params) {
		asynReq = new AsynReqImpl(url,HttpGet.METHOD_NAME);
		return asynReq.execReq(params);
	}
	
	/**
	 * Do get.
	 *
	 * @param url the url
	 * @return the string
	 */
	public static String doGetNotStop(String url) {
		return doGetNotStop(url,null,null,null);
	}
	
	/**
	 * Do get.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @return the string
	 */
	public static String get(String url,boolean shutdown) {
		return get(url, shutdown,null,null,null);
	}
	
	/**
	 * Do get.
	 *
	 * @param url the url
	 * @return the string
	 */
	public static String get(String url) {
		return get(url, true,null,null,null);
	}
	//参数是键值对的形式
	/**
	 * Do get.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param params the params
	 * @return the string
	 */
	public static String get(String url,boolean shutdown,Map<String,String> params) {
		return get(url, shutdown,null,params,null);
	}

	/**
	 * Do get.
	 *
	 * @param url the url
	 * @param params the params
	 * @return the string
	 */
	public static String get(String url,Map<String,String> params) {
		return get(url, true,null,params,null);
	}
	
	/**
	 * Do get.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param json the json
	 * @return the string
	 */
	public static String get(String url,boolean shutdown,String json) {
		return get(url,shutdown, null,json,null);
	}
	
	/**
	 * Do get.
	 *
	 * @param url the url
	 * @param json the json
	 * @return the string
	 */
	public static String get(String url,String json) {
		return get(url,true, null,json,null);
	}
	
	/**
	 * Do delete.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param params the params
	 * @return the string
	 */
	public static String doDelete(String url,boolean shutdown,Object...params) {
		asynReq = new AsynReqImpl(url,shutdown,HttpDelete.METHOD_NAME);
		return asynReq.execReq(params);
	}

	/**
	 * Do trace.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param params the params
	 * @return the string
	 */
	public static String doTrace(String url,boolean shutdown,Object...params){
		asynReq = new AsynReqImpl(url,shutdown,HttpTrace.METHOD_NAME);
		return asynReq.execReq(params);
	}
	
	/**
	 * Do options.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param params the params
	 * @return the string
	 */
	public static String doOptions(String url,boolean shutdown,Object...params){
		asynReq = new AsynReqImpl(url,shutdown,HttpOptions.METHOD_NAME);
		return asynReq.execReq(params);
	}
	
	public static class Callback{

		public Callback() {
			super();
			
		}
		
		public void closeService(){
			if(AsynReqImpl.getExeService()!=null)
				new AsynServices(AsynReqImpl.getExeService(),AsynReqImpl.getfReqExeService()).close();
		}
	}
}


