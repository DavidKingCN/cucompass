/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.util;

import java.util.Date;
/**
 * 
 * @author daikai
 * 
 */
public final class TimeKit {
	public static long getLastMillTimes(){
		return new Date().getTime();
	}
	
	public static double computSeconds(long millTimes){
		return millTimes/1000;
	}
	
	public static long computPeriod(long now,long last){
		return now - last;
	}
}
