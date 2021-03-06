/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtils {

    /**
     * 找到匹配的字符串
     * @return
     */
    public static String getMatchString(String src,String reg){
    	
         //String digitNumStr = "11A11";
         Pattern pattern = Pattern.compile(reg);
         Matcher matcher = pattern.matcher(src);
         String result = "";
         // Find all matches
         if (matcher.find()) {
           // Get the matching string
           result = matcher.group(1);
         }
    	return result;
    }
    
    /**
     * 找到匹配的字符串
     * @return
     */
    public static String getMatchString(String src,String reg,int size,int group){
    	
         //String digitNumStr = "11A11";
         Pattern pattern = Pattern.compile(reg);
         Matcher matcher = pattern.matcher(src);
         String result[] = new String[size];
         // Find all matches
         int i =0;
         while (matcher.find()) {
           // Get the matching string
//        	 result = matcher.group();
//        	 System.out.println(result);
        	 result[i++] = matcher.group();
         }
    	return result[group];
    }
    
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}