/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart;

import java.util.List;

public interface WebCrawler<T> {
	public List<T> getResultsByHtmlCrawler()throws Exception;
}
