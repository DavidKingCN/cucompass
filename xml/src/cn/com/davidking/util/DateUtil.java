/*
 *    功能名称   ： xml反序列化实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * 日期的工具类.
 *
 * @author DavidKing
 */
public class DateUtil {
	
	/** The Constant DATE_TIME_FMT. */
	public static final String DATE_TIME_FMT = "yyyy-MM-dd HH:mm:ss";
	
	/** The Constant DATE_FMT. */
	public static final String DATE_FMT = "yyyy-MM-dd";
	
	
	/**
	 * To string.
	 *
	 * @param dt the dt
	 * @return the string
	 */
	public static String toString(Date dt){
		if(dt==null)
			return "";
		return new SimpleDateFormat(DATE_TIME_FMT).format(dt);
	}
	//把当前时间转换成标准类型的time格式的字符串
	/**
	 * Now.
	 *
	 * @return the string
	 */
	public static String now(){
		return toString(new Date());
	}
	
	//toTime
	/**
	 * To time.
	 *
	 * @param str the str
	 * @return the date
	 * @throws Exception the exception
	 */
	public static Date toTime(String str) throws Exception{
		if(str==null || str.equals(""))
			return null;
		
		//格式校验
		return new SimpleDateFormat(DATE_TIME_FMT).parse(str);
	}
	
	/**
	 * 格式化string为Date.
	 *
	 * @param str the str
	 * @return date
	 */
	public static Date toDateLocaleUK(String str) {
		if (null == str || "".equals(str)) {
			return null;
		}
		try {
			String fmtstr = null;
			if (str.indexOf(':') > 0) {
				fmtstr = DateUtil.DATE_TIME_FMT;
			} else {
				fmtstr = DateUtil.DATE_FMT;
			}
			return new SimpleDateFormat(fmtstr, Locale.UK).parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 日期转化为String.
	 *
	 * @param date the date
	 * @return date string
	 */
	public static String toStringLocaleUK(Date date) {
		if (null == date) {
			return null;
		}
		try {
			return new SimpleDateFormat(DateUtil.DATE_TIME_FMT,	Locale.US).format(date);
		} catch (Exception e) {
			return null;
		}
	}

}
