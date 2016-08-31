/*
 *    功能名称   ： xml反序列化实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.util;

import java.text.NumberFormat;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * 字符串工具类.
 *
 * @author DavidKing
 */
public final class StringUtil {
	
	/** The Constant LOG. */
	public static final Logger LOG = Logger.getLogger(StringUtil.class);
	
	/**
	 * 截取最后一个空格之前的字符串.
	 *
	 * @param src the src
	 * @return the string split last blank
	 */
	public static String getStringSplitLastBlank(String src){
		if(src==null)
			return "";
		return src.substring(0, src.lastIndexOf(" "));
	}
	
	/**
	 * 得到百分比字符串.
	 *
	 * @param src the src
	 * @return the percent str
	 */
	public static String getPercentStr(double src){
		NumberFormat percentFmt = NumberFormat.getPercentInstance(); 
		percentFmt.setMaximumIntegerDigits(3); 
		percentFmt.setMaximumFractionDigits(2); 
		return percentFmt.format(src);
	}
	
	/**
	 * 首字母小写.
	 *
	 * @param input the input
	 * @return the string
	 */
	public static String lowerFirstLetter(String input){
		if(input==null || input .equals("")){
			
			LOG.warn("输入的字符串为空!");
			return "";
		}
		return Character.toLowerCase( input.charAt(0))+input.substring(1);
	}
}
