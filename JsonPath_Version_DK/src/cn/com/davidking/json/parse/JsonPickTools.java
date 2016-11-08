/*
 *    功能名称   ： json path实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.davidking.json.Constant;
import cn.com.davidking.json.util.JsonValidator;
import cn.com.davidking.json.util.MatchUtils;


// TODO: Auto-generated Javadoc
/**
 * The Class JsonPickTools.
 */
public abstract class JsonPickTools{
	
	/** The layer lens. */
	protected int layerLens  ;
	
	/** The target json. */
	protected String targetJson ;
	
	/** The rt map. */
	protected Map<String,Object> rtMap;
	
	/** The results. */
	protected List<String> results;
	
	/** The error. */
	protected boolean error;
	
	/** The is arr all. */
	protected boolean isArrAll;
	
	/** The is arr one. */
	protected boolean isArrOne ;
	
	/** The i. */
	protected int i;
	
	/** The node name. */
	protected String nodeName;
	
	/** The arr idx. */
	protected int arrIdx;
	
	
	/**
	 * The Constructor.
	 */
	public JsonPickTools() {
		super();
	}
	
	/**
	 * The Constructor.
	 *
	 * @param nodeName the node name
	 * @param targetJson the target json
	 * @param rtMap the rt map
	 */
	public JsonPickTools(String nodeName,String targetJson,Map<String,Object> rtMap){
		this.nodeName = nodeName;
		this.targetJson = targetJson;
		this.rtMap = rtMap;
	}
	
	/**
	 * The Constructor.
	 *
	 * @param isArrAll the is arr all
	 * @param nodeName the node name
	 * @param targetJson the target json
	 * @param error the error
	 */
	public JsonPickTools(boolean isArrAll, String nodeName, String targetJson, boolean error) {
		this.isArrAll = isArrAll;
		this.nodeName = nodeName;
		this.targetJson = targetJson;
		this.error = error;
	}
	
	/**
	 * The Constructor.
	 *
	 * @param isArrOne the is arr one
	 * @param arrIdx the arr idx
	 * @param nodeName the node name
	 * @param targetJson the target json
	 * @param error the error
	 */
	public JsonPickTools(boolean isArrOne,int arrIdx,String nodeName,String targetJson,boolean error){
		this.isArrOne = isArrOne;
		this.arrIdx = arrIdx;
		this.nodeName = nodeName;
		this.targetJson = targetJson;
		this.error = error;
	}
	
	/**
	 * The Constructor.
	 *
	 * @param layerLens the layer lens
	 * @param targetJson the target json
	 * @param rtMap the rt map
	 * @param results the results
	 * @param error the error
	 * @param isArrAll the is arr all
	 * @param isArrOne the is arr one
	 * @param i the i
	 * @param nodeName the node name
	 * @param arrIdx the arr idx
	 */
	public JsonPickTools(int layerLens, String targetJson, Map<String, Object> rtMap, List<String> results,
			boolean error, boolean isArrAll, boolean isArrOne, int i, String nodeName, int arrIdx) {
		super();
		this.layerLens = layerLens;
		this.targetJson = targetJson;
		this.rtMap = rtMap;
		this.results = results;
		this.error = error;
		this.isArrAll = isArrAll;
		this.isArrOne = isArrOne;
		this.i = i;
		this.nodeName = nodeName;
		this.arrIdx = arrIdx;
	}
	
	/**
	 * Gets the layer lens.
	 *
	 * @return the layer lens
	 */
	protected int getLayerLens() {
		return layerLens;
	}
	
	/**
	 * Sets the layer lens.
	 *
	 * @param layerLens the layer lens
	 */
	protected void setLayerLens(int layerLens) {
		this.layerLens = layerLens;
	}
	
	/**
	 * Gets the target json.
	 *
	 * @return the target json
	 */
	protected String getTargetJson() {
		return targetJson;
	}
	
	/**
	 * Sets the target json.
	 *
	 * @param targetJson the target json
	 */
	protected void setTargetJson(String targetJson) {
		this.targetJson = targetJson;
	}
	
	/**
	 * Gets the rt map.
	 *
	 * @return the rt map
	 */
	protected Map<String, Object> getRtMap() {
		return rtMap;
	}
	
	/**
	 * Sets the rt map.
	 *
	 * @param rtMap the rt map
	 */
	protected void setRtMap(Map<String, Object> rtMap) {
		this.rtMap = rtMap;
	}
	
	/**
	 * Gets the results.
	 *
	 * @return the results
	 */
	protected List<String> getResults() {
		return results;
	}
	
	/**
	 * Sets the results.
	 *
	 * @param results the results
	 */
	protected void setResults(List<String> results) {
		this.results = results;
	}
	
	/**
	 * Checks if is error.
	 *
	 * @return true, if checks if is error
	 */
	protected boolean isError() {
		return error;
	}
	
	/**
	 * Sets the error.
	 *
	 * @param error the error
	 */
	protected void setError(boolean error) {
		this.error = error;
	}
	
	/**
	 * Checks if is arr all.
	 *
	 * @return true, if checks if is arr all
	 */
	protected boolean isArrAll() {
		return isArrAll;
	}
	
	/**
	 * Sets the arr all.
	 *
	 * @param isArrAll the arr all
	 */
	protected void setArrAll(boolean isArrAll) {
		this.isArrAll = isArrAll;
	}
	
	/**
	 * Checks if is arr one.
	 *
	 * @return true, if checks if is arr one
	 */
	protected boolean isArrOne() {
		return isArrOne;
	}
	
	/**
	 * Sets the arr one.
	 *
	 * @param isArrOne the arr one
	 */
	protected void setArrOne(boolean isArrOne) {
		this.isArrOne = isArrOne;
	}
	
	/**
	 * Gets the i.
	 *
	 * @return the I
	 */
	protected int getI() {
		return i;
	}
	
	/**
	 * Sets the i.
	 *
	 * @param i the I
	 */
	protected void setI(int i) {
		this.i = i;
	}
	
	/**
	 * Gets the node name.
	 *
	 * @return the node name
	 */
	protected String getNodeName() {
		return nodeName;
	}
	
	/**
	 * Sets the node name.
	 *
	 * @param nodeName the node name
	 */
	protected void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	/**
	 * Gets the arr idx.
	 *
	 * @return the arr idx
	 */
	protected int getArrIdx() {
		return arrIdx;
	}
	
	/**
	 * Sets the arr idx.
	 *
	 * @param arrIdx the arr idx
	 */
	protected void setArrIdx(int arrIdx) {
		this.arrIdx = arrIdx;
	}
	
	/**
	 * 通过json数组产生json的list 对象集合
	 * [{"name":"张三","sex":"male"...},{..}...]转换成 n个{"name":"张三","sex":"male"...}串
	 *
	 * @param jsonArr the json arr
	 * @return the elements by json arr
	 */
	protected  List<String> getElementsByJsonArr(String jsonArr){
		int firstPos = jsonArr.indexOf(Constant.OPEN_BRACKET);
		int lastPos = jsonArr.lastIndexOf(Constant.CLOSE_BRACKET);
		
		
		
		String st = jsonArr.substring(firstPos+1, lastPos);
		st = st.trim();
		int lastNeedPos = st.lastIndexOf(Constant.CLOSE_BRACE);
		
		List<Integer> commaPs = new ArrayList<Integer>();
		
		int counter = 1;
		int pos = 1;
		boolean again=false;
		for(int i=1;i<st.length();i++){
			if(st.charAt(i)=='{'){
				if(again)
					continue;
				counter++;
			}else if(st.charAt(i)=='}'){
				counter--;
			}
			
			pos++;
			if(pos>lastNeedPos)
				break;
			if(counter==0&&st.charAt(pos)==','){
				commaPs.add(pos);
				counter = 1;
				again = true;
			}
			
		}
		commaPs.add(lastNeedPos+1);
		int frontInt=0;
		int lastInt;
		String nd = "";
		List<String> result = new ArrayList<String>();
		for(int i=0;i<commaPs.size();i++){
			if(i!=0){
				frontInt = commaPs.get(i-1);
			}
			lastInt = commaPs.get(i);
			if(i==0){
				nd = st.substring(frontInt, lastInt);
			}else{
				nd = st.substring(frontInt+1, lastInt);
			}
			result.add(nd);
		}
		
		return result;
	}
	
	
	//1st 2nd 3rd 4th
	/**
	 * 通过 ..key:[{"name":"张三"},{..},...].. 转化成n个{"name":"张三"}集合串
	 *
	 * @param json 要解析的json字符串
	 * @param key 需要处理的key值
	 * @return the elements by json arr
	 */
	protected  List<String> getElementsByJsonArr(String json,String key){
		int firstPos = json.indexOf(Constant.OPEN_BRACKET);
		int lastPos = json.lastIndexOf(Constant.CLOSE_BRACKET);
		
		String st = json.substring(firstPos+1, lastPos);
		st = st.trim();
		int lastNeedPos = st.lastIndexOf(Constant.CLOSE_BRACE);
		
		List<Integer> commaPs = new ArrayList<Integer>();
		
		int counter = 1;
		int pos = 1;
		boolean again=false;
		for(int i=1;i<st.length();i++){
			if(st.charAt(i)=='{'){
				if(again)
					continue;
				counter++;
			}else if(st.charAt(i)=='}'){
				counter--;
			}
			
			pos++;
			if(pos>lastNeedPos)
				break;
			if(counter==0&&st.charAt(pos)==','){
				commaPs.add(pos);
				counter = 1;
				again = true;
			}
			
		}
		commaPs.add(lastNeedPos);
		int frontInt=0;
		int lastInt;
		String nd = "";
		List<String> result = new ArrayList<String>();
		for(int i=0;i<commaPs.size();i++){
			if(i!=0){
				frontInt = commaPs.get(i-1);
			}
			lastInt = commaPs.get(i);
			if(i==0){
				nd = st.substring(frontInt, lastInt);
			}else{
				nd = st.substring(frontInt+1, lastInt);
			}
			result.add(getVals(nd,key));
		}
		
		return result;
	}
	
	/**
	 * *
	 * 需要解析的key值解析json串获取该key对应的值.
	 *
	 * @param json 要解析的json字符串
	 * @param key 指定需要的key值
	 * @return the vals
	 */
	private  String getVals(String json,String key){
		
		String splitReg = "\""+key+"\":";
		
		String[] splitArr = json.split(splitReg);
		
		if(splitArr.length==2){
			String targetSplit = splitArr[1];
			//去除前后空格
			return computClosureStr(targetSplit);
		}else{
			int splitBgInx = json.indexOf(splitReg);
			
			int splitRegLen = splitReg.length();
			
			int subStrBgInx = splitBgInx + splitRegLen+1;
			
			int jsonLen = json.length();
			
			String subStr = json.substring(subStrBgInx, jsonLen);
			
			return computClosureStr(subStr);
		}
	}
	
	/**
	 * Comput closure str.
	 *
	 * @param targetSplit the target split
	 * @return the string
	 */
	private  String computClosureStr(String targetSplit){
		targetSplit = targetSplit.trim();
		char firstChar = targetSplit.charAt(0);
		int counter = 1;
		int pos = 1;
		int targetLen = targetSplit.length();
		if(firstChar=='{'){
			for(int i=1;i<targetLen;i++){
				if(targetSplit.charAt(i)=='{'){
					counter++;
				}else if(targetSplit.charAt(i)=='}'){
					counter--;
				}
				
				pos++;
				if(counter==0){
					break;
				}
				
			}
			
			return targetSplit.substring(0, pos);
		} else if(firstChar=='['){
			for(int i=1;i<targetLen;i++){
				if(targetSplit.charAt(i)=='['){
					counter++;
				}else if(targetSplit.charAt(i)==']'){
					counter--;
				}
				
				pos++;
				if(counter==0){
					break;
				}
				
			}
			
			return targetSplit.substring(0, pos);
			
		} else if(firstChar=='"'){
			for(int i=1;i<targetLen;i++){
				
				pos++;
				if(targetSplit.charAt(i)=='"'){
					break;
				}
			}
			return targetSplit.substring(1, pos-1);
		} else {
			return MatchUtils.getOnlyMatchs(targetSplit, Constant.ZZS_REG);
		}
	}
	
	
	/**
	 * 根据指定的key值获取封闭的json值，但不含key.
	 *
	 * @param json 要解析的json字符串
	 * @param key 指定需要的key值
	 * @return the closure json
	 */
	protected  String getClosureJson(String json,String key){
		return getClosureJson(json,key,false);
	}
	
	/**
	 * Gets the closure json.
	 *
	 * @param json 要解析的json字符串
	 * @param key 指定需要的key值
	 * @param needPrefix 指定是否需要key值 true 需要 false 不需要
	 * @return the closure json
	 */
	protected  String getClosureJson(String json,String key,boolean needPrefix){
		
		if(json==null||json.equals("")){
			//LOG.error("json不能为空！");
			return "";
		}
		if(key==null||key.equals("")){
			//LOG.error("key值不能为空！");
			return "";
		}
		if(!JsonValidator.checkJsonValid(json)){
			//LOG.error("字符串非json串！");
			return "";
		}
		if(!json.contains(key)){
			//LOG.error("不含该key值！");
			return "";
		}
		
		
		String splitReg = "\""+key+"\":";
		
		String rt = getVals(json,key);
		
		String result = "";
		if(needPrefix){
			result+=Constant.OPEN_BRACE;
			result+=splitReg;
			result+=rt;
			result+=Constant.CLOSE_BRACE;
		}else{
			result = rt;
		}
		
		return result;
	}
	
	
}
