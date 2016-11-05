package cn.com.davidking.json;


public class Constant {
	public static final String javaNameRegExp = "[$_a-zA-Z]{1}[$_a-zA-Z0-9]+";
	
	public static final String mapAllRegExp = "\\{([$_a-zA-Z]{1}[$_a-zA-Z0-9]+,?)+\\}";
	public static final String javaArrAllRegExp = "[$_a-zA-Z]{1}[$_a-zA-Z0-9]+\\[\\*\\]";
	public static final String javaArrOneRegExp = "[$_a-zA-Z]{1}[$_a-zA-Z0-9]+\\[\\d+\\]";
	
	public static final String arrOneRegExp = "\\[(\\d+)\\]";
	
	public static final String arrAllRegExp = "\\[(\\*)\\]";
	
	public static final String regSeparator = "\\.";
	
	public static final String jsonEmptyArrRegExp ="\\[\\s*\\]"; 
	
	
	public static final String ZZS_REG = "\\d+";
	
	public static final String OPEN_BRACE = "{";
	public static final String CLOSE_BRACE = "}";
	
	public static final String OPEN_BRACKET = "[";
	public static final String CLOSE_BRACKET = "]";
	
	public static final String SINGLE_VALUE_KEY = "single";
	public static final String LIST_VALUE_KEY = "list";
	
	public static final String MAP_VALUE_KEY = "map";

}
