/*
 *    功能名称   ： xpath数据提取实现1.1
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.davidking.html.parse.XpathQuery;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;
import cn.com.davidking.http.core.$;
import cn.com.davidking.http.core.$.Callback;

// TODO: Auto-generated Javadoc
/**
 * The Class TestP2p.
 */
public class TestP2p {

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
//		ExecutorService exeService= Executors.newFixedThreadPool(4);
//		AsynReqImpl.setExeService(exeService);
//		String rt = AsynTemplate.doGet("https://list.lufax.com/list/all");
//		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
//		
		String url = "https://list.lu.com/list/all?currentPage=4";
		String rt = $.get(url, new Callback());
		List<Map<String,String>> results = 
				XpathQuery.newXpathQuery()
					.setHtml(rt)
					.setRootPath("//ul[@class='main-list']/li")
					.addSubPath("/dl/dt[@class='product-name']/a/@href[\\d+]")
					.addSubPath("/dl/dt[@class='product-name']/a/@title")
					.addSubPath("//dd/ul/li/p[@class='num-style']")
//					.addSubPath("//dd/ul/li[@class='invest-period']/p.text()//span/span[@class='taR']/a")
					.addSubPath("//dd/ul/li[@class='invest-period']/p.text()//span.text()")
					.query();
			
		results.forEach(result->{
			result.forEach((k,v)->{
				System.out.println(k+"<--->"+v);
			});
			System.out.println("");
		});
	}

}
