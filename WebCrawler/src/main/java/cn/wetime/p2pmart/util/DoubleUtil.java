/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.util;

public class DoubleUtil {
	public static String parseDouble(double amount) {

		String result = "";
		String dbStr = String.valueOf(amount);
		if (dbStr.matches("\\d.\\dE\\d")) {
			String digitStr = dbStr.split("E")[0];
			digitStr = digitStr.replace(".", "");
			int prefix = Integer.parseInt(digitStr);
			int bits = Integer.parseInt(dbStr.split("E")[1]);
			result += prefix;
			for(int i=0;i<bits-1;i++){
				result += "0";
			}
			result+=".0";
		}else{
			result = dbStr;
		}
		return result;
	}

}
