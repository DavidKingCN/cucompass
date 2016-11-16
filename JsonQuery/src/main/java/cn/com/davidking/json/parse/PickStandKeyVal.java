/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse;

import java.util.List;
import java.util.Map;

import cn.com.davidking.json.Constant;

// TODO: Auto-generated Javadoc
/**
 * The Class PickStandKeyVal.
 * 支持语义 为 $.xxxList[*].[<name,feature><,;>] 
 * 返回单值，有数组内的各个key（一个或多个）的值根据指定的分隔符拼串
 * 最简洁为 $.xxxList[*].[<name>]默认分隔符为,分隔符最多2个，否则返回空
 */
public class PickStandKeyVal extends JsonPickTools implements PickResult {

	
	/**
	 * The Constructor.
	 */
	public PickStandKeyVal() {
		super();
		// TODO Auto-generated constructor stub
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
	public PickStandKeyVal(int layerLens, Map<String, Object> rtMap, int i, String nodeName, ArgsTransition args) {
		super(layerLens, rtMap, i, nodeName, args);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPickTools#init(java.util.Map)
	 */
	@Override
	public void init(Map<String, Object> kvs) {
		super.init(kvs);
	}
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPicker#pick()
	 */
	@Override
	public void pick() {
		String targetKeysStr = nodeName.substring(nodeName.indexOf("[")+1, nodeName.lastIndexOf("]"));
		String targetKeys_ = targetKeysStr.substring(nodeName.indexOf("<"), nodeName.indexOf(">")-1);
		boolean containSep = nodeName.indexOf("<") != nodeName.lastIndexOf("<");
		String[] seps = null;
		int sepLen = 1;
		String singleSep = Constant.Stand_Separator;
		if(containSep){
			String sepStr = targetKeysStr.substring(nodeName.lastIndexOf("<"), nodeName.lastIndexOf(">")-1);
			sepLen = sepStr.length();
			if(sepLen>1){
				seps = new String[sepLen];
				for(int i=0;i<sepLen;i++){
					seps[i] = sepStr.charAt(i)+"";
				}
			}else{
				singleSep = sepStr;
			}
		}
		String[] targetKeys = null;
		String tk = "";
		if(targetKeys_.contains(",")){
			targetKeys = targetKeys_.split(",");
		}else
			tk = targetKeys_;
		
		if(sepLen>2){
			rtMap.put(Constant.SINGLE_VALUE_KEY, null);
			return;
		}
		
		List<String>jsons = getElementsByJsonArr(args.getTargetJson());
		StringBuffer standRt = new StringBuffer();
		if(layerLens==1){
			if(targetKeys!=null && targetKeys.length>=1){
				standRt = multiKey(jsons,targetKeys,seps,sepLen,containSep,singleSep,standRt);
			}else{
				standRt = singleKey(jsons,tk,containSep,singleSep,standRt);
			}
		}else{
			if(targetKeys!=null && targetKeys.length>=1){
				standRt = multiKey(jsons,targetKeys,seps,sepLen,containSep,singleSep,standRt);
				
			}else{
				standRt = singleKey(jsons,tk,containSep,singleSep,standRt);
			}
		}
		rtMap.put(Constant.SINGLE_VALUE_KEY, standRt);
	}

	

	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonResult#result()
	 */
	@Override
	public Map<String, Object> result() {
		return this.rtMap;
	}

	/**
	 * Multi key.
	 *
	 * @param jsons the jsons
	 * @param targetKeys the target keys
	 * @param seps the seps
	 * @param sepLen the sep len
	 * @param containSep the contain sep
	 * @param singleSep the single sep
	 * @param standRt the stand rt
	 * @return the string buffer
	 */
	private StringBuffer multiKey(List<String> jsons,String[] targetKeys,String[] seps,int sepLen,boolean containSep,String singleSep,StringBuffer standRt){
		int idx = 0;
		for(String json:jsons){
			int jdx = 0;
			for(String targetKey:targetKeys){
				standRt.append(getClosureJson(json, targetKey));
				if(sepLen==2)
					standRt.append(jdx==targetKeys.length-1?"":seps[0]);
				else if(containSep)
					standRt.append(jdx==targetKeys.length-1?"":singleSep);
				jdx++;
			}
			if(sepLen==2)
				standRt.append(idx==jsons.size()-1?"":seps[1]);
			else if(containSep)
				standRt.append(idx==jsons.size()-1?"":singleSep);
			idx ++;
		}
		return standRt;
	}
	
	/**
	 * Single key.
	 *
	 * @param jsons the jsons
	 * @param tk the tk
	 * @param containSep the contain sep
	 * @param sep the sep
	 * @param standRt the stand rt
	 * @return the string buffer
	 */
	private StringBuffer singleKey(List<String> jsons,String tk,boolean containSep,String sep,StringBuffer standRt){
		int idx = 0;
		for(String json:jsons){
			standRt.append(getClosureJson(json, tk));
			if(!containSep)
				standRt.append(idx++==jsons.size()-1?"":Constant.Stand_Separator);
			else
				standRt.append(sep);
		}
		
		return standRt;
	}
}
