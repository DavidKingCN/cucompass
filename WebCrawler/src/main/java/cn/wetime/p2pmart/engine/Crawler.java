/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import cn.wetime.p2pmart.WebCrawler;
import cn.wetime.p2pmart.pojo.Product;
@SuppressWarnings("all")
public class Crawler<T> implements Observer, Callable<Map<Boolean,List<T>>> {
	
	private WebCrawler webCrawler;
//	private long last;
	private boolean hasDone=false;
//	private boolean timeout=false;
	
	long interval = 0;//间隔时间跨度
	
	//注册
	private static void register(){
	}
	static{
		register();
	}
	public Crawler() {
		super();
	}
	
	public Crawler(WebCrawler webCrawler) {
		super();
		this.webCrawler = webCrawler;
	}
	//通知方法
	public boolean update(Observable o, Object arg) {
		System.out.println("被通知了...");
		return true;
	}
	//线程调用业务方法
	public Map<Boolean,List<T>> call() throws Exception {
		Map<Boolean,List<T>> result = null;
		try {
			synchronized (this) {
				while(!hasDone){
					if(!hasDone){
						result = this.doCrawler();
						hasDone = true;
					}
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{

		}
		return result;
	}
	//执行委托类的业务方法
	private synchronized Map<Boolean,List<T>> doCrawler(){
		Map<Boolean,List<T>> result = new HashMap<Boolean,List<T>>();
		boolean success = true;
		List<T> products = null;
		try {
			products = webCrawler.getResultsByHtmlCrawler();
		} catch (Exception e) {
			System.out.println("爬虫抓取异常啦...");
			success = false;
			products = null;
			
		}
		result.put(success, products);
        return result;
	}
}



