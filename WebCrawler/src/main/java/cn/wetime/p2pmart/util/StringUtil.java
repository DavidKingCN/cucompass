/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.util;

import java.text.NumberFormat;

/**
 * 字符串工具类
 * @author daikai
 *
 */
public final class StringUtil {
	/**
	 * 截取最后一个空格之前的字符串
	 * @param src
	 * @return
	 */
	public static String getStringSplitLastBlank(String src){
		if(src==null)
			return "";
		return src.substring(0, src.lastIndexOf(" "));
	}
	/**
	 * 得到百分比字符串
	 * @param src
	 * @return
	 */
	public static String getPercentStr(double src){
		NumberFormat percentFmt = NumberFormat.getPercentInstance(); 
		percentFmt.setMaximumIntegerDigits(3); 
		percentFmt.setMaximumFractionDigits(2); 
		return percentFmt.format(src);
	}
}
