/*
 *    功能名称   ： httpclient 封裝实现1.2
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package asynRq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.impl.client.FutureRequestExecutionService;

import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.$;
import cn.com.davidking.http.core.ResourceClose;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 */
public class Test$ {

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		//The right way to close the resources ....
		
		//conf thread pool..
		$.setExecutor(Executors.newFixedThreadPool(4));
		
		//exe req..
		String rt = $.get("http://www.sina.com");
		
		//release thread pool resource..
		$.closeExecutor();
		
		//print the exe result in console...
		System.out.println(rt);
	}

}
