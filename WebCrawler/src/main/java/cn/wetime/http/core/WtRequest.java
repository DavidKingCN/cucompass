/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.http.core;


/**
 * 请求执行接口及常量
 * 
 * @author daikai
 * @version 1.0
 */
public interface WtRequest {
	public static final int SUCCESS = 0;

	public static final int FAIL = 1;
	
	public static final int NOT_MATCH = -1;				//获取内容类型与需求类型不匹配

	public static final int ACCESS_TOKEN_INVALID = 2;

	public String exeRequest(Object...params);
	

}
