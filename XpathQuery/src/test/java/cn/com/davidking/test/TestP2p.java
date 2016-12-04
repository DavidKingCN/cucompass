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
		ExecutorService exeService= Executors.newFixedThreadPool(4);
		AsynReqImpl.setExeService(exeService);
		//AsynReqImpl.setCharset("utf8");
		String rt = AsynTemplate.doGet("https://list.lufax.com/list/all");
		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
		
		List<Map<String,String>> results = 
				XpathQuery.newXpathQuery()
					.setHtml(rt)
					.setRootPath("//ul[@class='main-list']/li")
					.addSubPath("//dt[@class='product-name']/a/@href")
					.addSubPath("//dt[@class='product-name']/a")
					.addSubPath("//dd/ul/li/p[@class='num-style']")
					.query();
			
		results.forEach(result->{
			result.forEach((k,v)->{
				System.out.println(k+"---"+v);
			});
		});
	}

}
