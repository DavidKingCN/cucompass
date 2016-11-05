package cn.com.davidking.json.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.davidking.json.Constant;
import cn.com.davidking.json.util.JsonValidator;
import cn.com.davidking.json.util.MatchUtils;


public abstract class JsonPullerManager{
	protected int layerLens  ;
	
	protected String targetJson ;
	
	protected Map<String,Object> rtMap;
	protected List<String> results;
	protected boolean error;
	
	protected boolean isArrAll;
	protected boolean isArrOne ;
	protected int i;
	protected String nodeName;
	protected int arrIdx;
	
	
	public JsonPullerManager() {
		super();
	}
	
	public JsonPullerManager(String nodeName,String targetJson,Map<String,Object> rtMap){
		this.nodeName = nodeName;
		this.targetJson = targetJson;
		this.rtMap = rtMap;
	}
	
	public JsonPullerManager(boolean isArrAll, String nodeName, String targetJson, boolean error) {
		this.isArrAll = isArrAll;
		this.nodeName = nodeName;
		this.targetJson = targetJson;
		this.error = error;
	}
	public JsonPullerManager(boolean isArrOne,int arrIdx,String nodeName,String targetJson,boolean error){
		this.isArrOne = isArrOne;
		this.arrIdx = arrIdx;
		this.nodeName = nodeName;
		this.targetJson = targetJson;
		this.error = error;
	}
	public JsonPullerManager(int layerLens, String targetJson, Map<String, Object> rtMap, List<String> results,
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
	
	public int getLayerLens() {
		return layerLens;
	}
	public void setLayerLens(int layerLens) {
		this.layerLens = layerLens;
	}
	public String getTargetJson() {
		return targetJson;
	}
	public void setTargetJson(String targetJson) {
		this.targetJson = targetJson;
	}
	public Map<String, Object> getRtMap() {
		return rtMap;
	}
	public void setRtMap(Map<String, Object> rtMap) {
		this.rtMap = rtMap;
	}
	public List<String> getResults() {
		return results;
	}
	public void setResults(List<String> results) {
		this.results = results;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public boolean isArrAll() {
		return isArrAll;
	}
	public void setArrAll(boolean isArrAll) {
		this.isArrAll = isArrAll;
	}
	public boolean isArrOne() {
		return isArrOne;
	}
	public void setArrOne(boolean isArrOne) {
		this.isArrOne = isArrOne;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getArrIdx() {
		return arrIdx;
	}
	public void setArrIdx(int arrIdx) {
		this.arrIdx = arrIdx;
	}
	/**
	 * 通过json数组产生json的list 对象集合
	 * [{"name":"张三","sex":"male"...},{..}...]转换成 n个{"name":"张三","sex":"male"...}串
	 * @param jsonArr
	 * @return
	 */
	public  List<String> getElementsByJsonArr(String jsonArr){
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
	 * @param json 要解析的json字符串
	 * @param key 需要处理的key值
	 * @return
	 */
	public  List<String> getElementsByJsonArr(String json,String key){
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
	
	/***
	 * 需要解析的key值解析json串获取该key对应的值
	 * @param json 要解析的json字符串
	 * @param key 指定需要的key值
	 * @return
	 */
	public  String getVals(String json,String key){
		
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
	 * 
	 * @param targetSplit
	 * @return
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
	 * 根据指定的key值获取封闭的json值，但不含key
	 * @param json 要解析的json字符串
	 * @param key 指定需要的key值
	 * @return
	 */
	public  String getClosureJson(String json,String key){
		return getClosureJson(json,key,false);
	}
	/**
	 * 
	 * @param json 要解析的json字符串
	 * @param key 指定需要的key值
	 * @param needPrefix 指定是否需要key值 true 需要 false 不需要
	 * @return
	 */
	public  String getClosureJson(String json,String key,boolean needPrefix){
		
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
