/*
 *    功能名称   ： httpclient 封裝实现1.2
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package httpRq;

import cn.com.davidking.http.core.HttpTemplate;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 *
 * @author daikai
 */
public class Test {
	
	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
//		test001();
		test002();
	}
	
	/**
	 * Test001.
	 */
	public static void test001(){
		String url = "http://localhost:8089/beidou/user/login";
		
		String rt = HttpTemplate.doPost(
				url,
				"{\"username\":\"zhangsan\",\"password\":\"1\"}");
		
		System.out.println(rt);
	}
	
	/**
	 * Test002.
	 */
	public static void test002(){
		String url = "http://www.quartz-scheduler.org/downloads/";

		String rt = HttpTemplate.doGet(url);
		
		System.out.println(rt);
	}
}
