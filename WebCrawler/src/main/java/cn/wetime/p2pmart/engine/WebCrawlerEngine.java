/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.engine;

import java.util.List;

import cn.wetime.p2pmart.WebCrawler;
import cn.wetime.p2pmart.pojo.Product;

@SuppressWarnings("all")
public class WebCrawlerEngine {
	private static WebCrawlerEngine webCrawlerEngine = new WebCrawlerEngine();

	private WebCrawlerEngine() {
	}

	public static WebCrawlerEngine newInstance() {
		if (webCrawlerEngine == null)
			webCrawlerEngine = new WebCrawlerEngine();
		return webCrawlerEngine;
	}
	//业务方法单利只有一个
	public<T> List<T> doCrawler(){
		
		Observable monitor = new CrawlerMonitor();
		
		for(WebCrawler webCrawler:BaseCrawlContext.WEB_CRAWLER_CONTEXT){
		Observer observer = new Crawler(webCrawler);
			monitor.addObserver(observer);
		}
		monitor.notifyObservers();
		
		return monitor.exeCallback();
	}
	
	
}
