/*
 *    功能名称   ： httpclient 封裝实现1.2
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package asynRq;

import cn.com.davidking.http.core.$;
import cn.com.davidking.http.core.$.Callback;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 */
public class Test$Get {

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		
		String rt = $.get("http://www.sina.com",new Callback(){});
		
		//print the exe result in console...
		System.out.println(rt);
	}

}
