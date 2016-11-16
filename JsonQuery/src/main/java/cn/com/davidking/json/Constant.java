/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json;


// TODO: Auto-generated Javadoc
/**
 * The Class Constant.
 */
public class Constant {
	
	/** The Constant javaNameRegExp. */
	public static final String javaNameRegExp = "[$_a-zA-Z]{1}[$_a-zA-Z0-9]+";
	
	/** The Constant mapSingleVByReg. */
	public static final String mapSingleVByReg = "[$_a-zA-Z]{1}[$_a-zA-Z0-9]+\\(.*?\\)";
	/** The Constant mapAllRegExp. */
	public static final String mapStandRegExp = "\\[<([$_a-zA-Z]{1}[$_a-zA-Z0-9]+,?)+>(<[~!@#$%&\\-,;]+>)?\\]";
	/** The Constant mapAllRegExp. */
	public static final String mapAllRegExp = "\\{([$_a-zA-Z]{1}[$_a-zA-Z0-9]+,?)+\\}";
	
	/** The Constant javaArrAllRegExp. */
	public static final String javaArrAllRegExp = "[$_a-zA-Z]{1}[$_a-zA-Z0-9]+\\[\\*\\]";
	
	/** The Constant javaArrOneRegExp. */
	public static final String javaArrOneRegExp = "[$_a-zA-Z]{1}[$_a-zA-Z0-9]+\\[\\d+\\]";
	
	/** The Constant arrOneRegExp. */
	public static final String arrOneRegExp = "\\[(\\d+)\\]";
	
	/** The Constant arrAllRegExp. */
	public static final String arrAllRegExp = "\\[(\\*)\\]";
	
	/** The Constant regSeparator. */
	public static final String regSeparator = "\\.";
	
	/** The Constant Stand_Separator. */
	public static final String Stand_Separator = ",";
	
	/** The Constant jsonEmptyArrRegExp. */
	public static final String jsonEmptyArrRegExp ="\\[\\s*\\]"; 
	
	
	/** The Constant ZZS_REG. */
	public static final String ZZS_REG = "\\d+";
	
	/** The Constant OPEN_BRACE. */
	public static final String OPEN_BRACE = "{";
	
	/** The Constant CLOSE_BRACE. */
	public static final String CLOSE_BRACE = "}";
	
	/** The Constant OPEN_BRACKET. */
	public static final String OPEN_BRACKET = "[";
	
	/** The Constant CLOSE_BRACKET. */
	public static final String CLOSE_BRACKET = "]";
	
	/** The Constant SINGLE_VALUE_KEY. */
	public static final String SINGLE_VALUE_KEY = "single";
	
	/** The Constant LIST_VALUE_KEY. */
	public static final String LIST_VALUE_KEY = "list";
	
	/** The Constant MAP_VALUE_KEY. */
	public static final String MAP_VALUE_KEY = "map";

}
