/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class MatchUtils.
 */
public class MatchUtils {

	/**
	 * 找到匹配的字符串.
	 *
	 * @param src the src
	 * @param reg the reg
	 * @return the match string
	 */
	public static String getMatchString(String src, String reg) {

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(src);
		String result = "";
		if (matcher.find()) {
			result = matcher.group(0);
		}
		return result;
	}

	/**
	 * Gets the match string.
	 *
	 * @param src the src
	 * @param reg the reg
	 * @param group the group
	 * @return the match string
	 */
	public static List<String> getMatchString(String src, String reg, int group) {

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(src);
		List<String> result = new ArrayList<String>();
		while (matcher.find()) {
			result.add(matcher.group(group));
		}
		return result;
	}
	
	/**
	 * Gets the match string.
	 *
	 * @param src the src
	 * @param reg the reg
	 * @param groups the groups
	 * @return the match string
	 */
	public static List<List<String>> getMatchString(String src, String reg, int... groups) {

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(src);
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> temp = null;
		while (matcher.find()) {
			temp = new ArrayList<String>();
			for(int group:groups){
				temp.add(matcher.group(group));
			}
			result.add(temp);
		}
		return result;
	}

	/**
	 * Gets the matchs array.
	 *
	 * @param src the src
	 * @param reg the reg
	 * @return the matchs array
	 */
	public static String[] getMatchsArray(String src, String reg) {

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(src);
		List<String> strs = new ArrayList<String>();
		int index = 0;
		while (matcher.find()) {
			strs.add(matcher.group());
			index++;
		}
		return strs.toArray(new String[index]);
	}
	
	
	/**
	 * Gets the matchs array.
	 *
	 * @param src the src
	 * @param reg the reg
	 * @param group the group
	 * @return the matchs array
	 */
	public static String[] getMatchsArray(String src, String reg,int group) {

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(src);
		List<String> strs = new ArrayList<String>();
		int index = 0;
		while (matcher.find()) {
			strs.add(matcher.group(group));
			index++;
		}
		return strs.toArray(new String[index]);
	}

	/**
	 * Gets the only matchs.
	 *
	 * @param src the src
	 * @param reg the reg
	 * @return the only matchs
	 */
	public static String getOnlyMatchs(String src, String reg) {
		String[] rts = getMatchsArray(src, reg);
		if (rts == null || rts.length == 0) {

			return "";
		}
		return rts[0];
	}

	/**
	 * Gets the only matchs.
	 *
	 * @param src the src
	 * @param reg the reg
	 * @param group the group
	 * @return the only matchs
	 */
	public static String getOnlyMatchs(String src, String reg, int group) {
		List<String> rts = getMatchString(src, reg, group);
		if (rts == null || rts.size() == 0) {

			return "";
		}
		return rts.get(0);
	}

	/**
	 * Replace blank.
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * Gets the single val by reg.
	 *
	 * @param str the str
	 * @param regEx the reg ex
	 * @return the single val by reg
	 */
	public static String getSingleValByReg(String str,String regEx){
		return getSingleValByReg(str,regEx,1);
	}
	
	/**
	 * *.
	 *
	 * @param str the str
	 * @param regEx the reg ex
	 * @param indx the indx
	 * @return the single val by reg
	 */
	public static String getSingleValByReg(String str,String regEx,int indx){
		String rt = null;
		if(str==null || str.equals("")){
			//TODO LOG..
			return rt;
		}
		if(regEx ==null || regEx.equals("")){
			//TODO LOG..
			return rt;
		}
		
		if(indx <1 || indx >9){
			//TODO LOG..
			return rt;
		}
			
		try {
			rt = MatchUtils.getOnlyMatchs(str, regEx, indx);
		} catch (Exception e) {
			return rt;
		}
		return rt;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		String text = "https://insurance.lu.com/qweqwe/product/32973272/detail";
		System.out.println(getOnlyMatchs(text, "\\d+"));

		String json = "{\"totalNum\":\"32\"}";

		System.out.println(getOnlyMatchs(json, "\\d+"));

	}
}