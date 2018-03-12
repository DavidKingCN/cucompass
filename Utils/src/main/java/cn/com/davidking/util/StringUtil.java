/*
 *    功能名称   ： 工具类 封裝实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.util;

import java.text.NumberFormat;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * 字符串工具类.
 *
 * @author daikai
 */
public final class StringUtil {
	
	public static String upperFirstCapital(String src){
		return Character.toUpperCase(src.charAt(0))+src.substring(1);
	}
	
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
	 * Gets the arr by token.
	 *
	 * @param src the src
	 * @param regEx the reg ex
	 * @return the arr by token
	 */
	public static String[] getArrByToken(String src,String regEx){
		
		StringTokenizer st = new StringTokenizer(src, regEx);
		String[] ss = new String[st.countTokens()];
		int i=0;
		while(st.hasMoreTokens())
			ss[i++]=st.nextToken();
		return ss;
	}
	
	/**
	 * Contain cn blank.
	 *
	 * @param src the src
	 * @return true, if contain cn blank
	 */
	public static boolean containCnBlank(String src){
		if(src==null||src.equals(""))
			return false;
		return src.matches("　+"); 
	}
	
	/**
	 * Removes the cn blank.
	 *
	 * @param src the src
	 * @return the string
	 */
	public static String removeCnBlank(String src){
		if(src==null||src.equals(""))
			return "";
		return src.replaceAll("　", "");
	}
	/**
	 * 是否含有全角字符
	 * @param src
	 * @return
	 */
	public static boolean containSBC(String src){
		return src.matches(MatchUtils.SBC_REG_EXP);
	}
	
	//replace special symbols
	public static String replSpclSymbols(String src){
		src = src.replaceAll("👍", " ")//
				 .replaceAll("🏻", " ")
				 .replaceAll("😜", " ")
				 .replaceAll("😄", " ")
				 .replaceAll("😁", " ")
				 .replaceAll("😀", " ")
				 .replaceAll("💭", " ")
				 .replaceAll("👣", " ")
				 .replaceAll("😝", " ")
				 .replaceAll("🌺", " ")
				 .replaceAll("💡", " ")
				 .replaceAll("✨", " ")
				 .replaceAll("₃", " ")
				 .replaceAll("💕", " ");//😍
		
		
		return src;
	}
	/**
     * 全角字符串转换半角字符串
     * 
     * @param src
     *            非空的全角字符串
     * @return 半角字符串
     */
    public static String sbcToDbcStr(String src) {
        if (null == src || src.length() <= 0) {
            return "";
        }
        char[] charArray = src.toCharArray();
        //对全角字符转换的char数组遍历
        for (int i = 0; i < charArray.length; ++i) {
            int charIntValue = (int) charArray[i];
            //如果符合转换关系,将对应下标之间减掉偏移量65248;如果是空格的话,直接做转换
            if (charIntValue >= 65281 && charIntValue <= 65374) {
                charArray[i] = (char) (charIntValue - 65248);
            } else if (charIntValue == 12288) {
                charArray[i] = (char) 32;
            }
        }
        return new String(charArray);
    }
    public static String getJpgFileName(String url){
    	String jpgName = "";
    	String regExp = "/([a-zA-Z0-9]+\\.jpg)";
    	jpgName = MatchUtils.getOnlyMatchs(url, regExp,1);
    	return jpgName;
    }
    public static String getSuffixByUrl(String url){
    	String suffix = "";
    	int idx = url.lastIndexOf("/");
    	String fileName = url.substring(idx+1);
    	if(fileName.matches("[a-zA-Z0-9]+\\.[a-zA-Z]+(\\?\\d+)?")){
    		int idx2 = fileName.lastIndexOf(".");
    		String tmp = fileName.substring(idx2+1);
    		if(tmp.contains("?")){
    			int idx3 = tmp.lastIndexOf("?");
    			suffix = tmp.substring(0, idx3);
    		}
    	}
    	return suffix;
    }
    
}
