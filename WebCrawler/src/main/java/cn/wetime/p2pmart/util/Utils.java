/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import cn.wetime.p2pmart.jdbc.JdbcUtils;


public class Utils {

	private static final String JDBC_PROPERTIES_FILENAME = "/config.properties";
	
	public static long getCurrMillSeconds(){
		return System.currentTimeMillis();
	}

	
	public static void loadJdbcProperties(String filePath)throws Exception{
		Properties p = getPropertiesByFilePath(filePath+"WEB-INF/classes" + JDBC_PROPERTIES_FILENAME);
		JdbcUtils.setUrl(p.getProperty("jdbc.url")); 
		JdbcUtils.setUsername(p.getProperty("jdbc.username")); 
		JdbcUtils.setPassword(p.getProperty("jdbc.password")); 
	}
	
	public static Properties getPropertiesByFilePath(String filePath)throws Exception{
		InputStream in  = null;
		Properties p = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath)); 
			p = new Properties(); 
			p.load(in);
			  
		} finally{
				in.close();
		}
		return p;
	}
}
