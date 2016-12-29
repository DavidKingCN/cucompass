/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cn.com.davidking.json.Constant;
import cn.com.davidking.util.JsonValidator;
import cn.com.davidking.util.MatchUtils;


// TODO: Auto-generated Javadoc
/**
 * The Class JsonPickTools.
 */
@SuppressWarnings("all")
public abstract class JsonPickTools{
	
	public static final Logger LOG = Logger.getLogger(JsonPickTools.class);
	
	protected static final String MARK_SEP = "#;#";
	
	/** The layer lens. */
	protected int layerLens  ;

	
	/** The rt map. */
	protected Map<String,Object> rtMap;
	
	
	/** The i. */
	protected int i;
	
	/** The node name. */
	protected String nodeName;
	
	/** The args. */
	protected ArgsTransition args;
	/**
	 * The Constructor.
	 */
	public JsonPickTools() {
		super();
	}
	
	/**
	 * The Constructor.
	 *
	 * @param layerLens the layer lens
	 * @param rtMap the rt map
	 * @param i the i
	 * @param nodeName the node name
	 * @param args the args
	 */
	public JsonPickTools(int layerLens, Map<String, Object> rtMap, int i, String nodeName, ArgsTransition args) {
		this.layerLens = layerLens;
		this.rtMap = rtMap;
		this.i = i;
		this.nodeName = nodeName;
		this.args = args;
		
	}
	
	/**
	 * Inits the.
	 *
	 * @param kvs the kvs
	 */
	protected void init(Map<String,Object> kvs){
		this.setLayerLens((Integer)kvs.get("layerLens"));
		this.setRtMap((Map<String,Object>)kvs.get("rtMap"));
		this.setI((Integer)kvs.get("i"));
		this.setNodeName(kvs.get("nodeName").toString());
		this.setArgs((ArgsTransition)kvs.get("args"));
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
	 * Gets the args.
	 *
	 * @return the args
	 */
	public ArgsTransition getArgs() {
		return args;
	}

	/**
	 * Sets the args.
	 *
	 * @param args the args
	 */
	public void setArgs(ArgsTransition args) {
		this.args = args;
	}

	/**
	 * 通过json数组产生json的list 对象集合
	 * [{"name":"张三","sex":"male"...},{..}...]转换成 n个{"name":"张三","sex":"male"...}串
	 *
	 * @param jsonArr the json arr
	 * @return the elements by json arr
	 */
	protected  List<String> getElementsByJsonArr(String jsonArr){
		return getElementsByJsonArr(jsonArr,null);
	}
	
	
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
		
		List<Integer> commaPs = new ArrayList<>();
		
		int counter = 1;
		int pos = 0;
		boolean again=true;
		for(int i=0;i<st.length();i++){
			if(st.charAt(i)=='{'){
				if(again){
					pos++;again = false;continue;
				}
				counter++;
			}else if(st.charAt(i)=='}')	counter--;
			
			pos++;
			if(pos>lastNeedPos)	break;
			if(counter==0&&st.charAt(pos)==','){
				commaPs.add(pos);counter = 1;again = true;
			}
			
		}
		commaPs.add(lastNeedPos+1);
		int frontInt=0;
		int lastInt;
		String nd = "";
		List<String> result = new ArrayList<String>();
		for(int i=0;i<commaPs.size();i++){
			if(i!=0)frontInt = commaPs.get(i-1);
			lastInt = commaPs.get(i);
			if(i==0)nd = st.substring(frontInt, lastInt);
			else nd = st.substring(frontInt+1, lastInt);
			
			if(key!=null&&!key.equals(""))
				result.add(getVals(nd,key));
			else result.add(nd);
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
				if(targetSplit.charAt(i)=='{')
					counter++;
				else if(targetSplit.charAt(i)=='}')
					counter--;
				pos++;
				if(counter==0) break;
			}
			
			return targetSplit.substring(0, pos);
		} else if(firstChar=='['){
			for(int i=1;i<targetLen;i++){
				if(targetSplit.charAt(i)=='[')
					counter++;
				else if(targetSplit.charAt(i)==']')
					counter--;
				
				pos++;
				if(counter==0) break;
				
			}
			return targetSplit.substring(0, pos);
			
		} else if(firstChar=='"'){
			for(int i=1;i<targetLen;i++){
				pos++;
				if(targetSplit.charAt(i-1)!='\\'&&targetSplit.charAt(i)=='"')
					break;
			}
			return targetSplit.substring(1, pos-1);
		} else 
			return MatchUtils.getOnlyMatchs(targetSplit, Constant.ZZS_REG);
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
			LOG.error("json不能为空！");
			return "";
		}
		if(key==null||key.equals("")){
			LOG.error("key值不能为空！");
			return "";
		}
		if(!JsonValidator.checkJsonValid(json)){
			LOG.error("字符串非json串！");
			return "";
		}
		if(!json.contains(key)){
			LOG.error("不含该key值！");
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
		}else result = rt;
		
		return result;
	}
	//cut 
	protected String cutByClosedChar(String json,char bgChar,char endChar){
		
		String procStr = json.trim();
		int counter = 1;
		int pos = 1;
		boolean again=true;
		int targetLen = procStr.length();
		int firstPos = -1;
		int endPos = -1;
		Map<Integer,Integer> posNeeds = new HashMap<Integer,Integer>();
		for(int i=1;i<targetLen-1;i++){
			if(procStr.charAt(i)!='\\'&&procStr.charAt(i)==bgChar){
				if(again){
					firstPos=pos++;again = false;continue;
				}
				counter++;
			} else if(procStr.charAt(i)!='\\'&&procStr.charAt(i)==endChar)
				counter--;
			pos++;
			if(pos>=targetLen-1)
				break;
			if(counter==0){
				endPos = pos;counter = 1;again = true;
				//记忆前后位置就可以了...
				posNeeds.put(firstPos, endPos);
			}
		}
		List<String> rpls = new ArrayList<String>();
		if(posNeeds.size()>0){
			for(Entry<Integer,Integer> posNeed:posNeeds.entrySet()){
				rpls.add(procStr.substring(posNeed.getKey(), posNeed.getValue()));
			}
		}
		for(String rpl:rpls){
			if(!rpl.matches("\\"+bgChar+"\\s*\\"+endChar))
			procStr = procStr.replace(rpl,"\"\"");
		}
		return procStr;
	}
	
	protected String cutAll(String json){
		String rt = json;
		try {
			rt = cutByClosedChar(json, '[', ']');
			rt = cutByClosedChar(rt, '{', '}');
		} catch (Exception ignore) {LOG.error("处理层级json出错了!");}
		return rt;
	}
}
