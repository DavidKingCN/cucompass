/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.timer;

import org.springframework.beans.factory.annotation.Autowired;

import cn.wetime.p2pmart.service.ProductService;

public final class ScanTimer {
	private static ScanTimer timer = new ScanTimer();
	private ScanTimer(){}
	public static ScanTimer newInstance(){
		if(timer==null)
			timer = new ScanTimer();
		return timer;
	}
	@Autowired
	ProductService productService;
	private static int counter ;
	public void execute(){
		productService.saveOrUpdateBatchProducts();
		System.out.println(productService.toString()+" 计数器："+counter++);
	}
}
