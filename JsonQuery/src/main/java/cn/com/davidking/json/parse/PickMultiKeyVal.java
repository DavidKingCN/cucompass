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

import cn.com.davidking.json.Constant;


// TODO: Auto-generated Javadoc
/**
 * 语义 $.xxList[*].{name,id} 返回含有<name,name值><id,id值>的List
 * The Class PickMultiKeyVal.
 */
public class PickMultiKeyVal extends JsonPickTools implements PickResult{
	
	/**
	 * The Constructor.
	 */
	public PickMultiKeyVal(){}
	
	/**
	 * The Constructor.
	 *
	 * @param layerLens the layer lens
	 * @param rtMap the rt map
	 * @param i the i
	 * @param nodeName the node name
	 * @param args the args
	 */
	public PickMultiKeyVal(int layerLens, Map<String, Object> rtMap, int i, String nodeName, ArgsTransition args) {
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
		String targetKeysStr = nodeName.substring(nodeName.indexOf("{")+1, nodeName.lastIndexOf("}"));
		String[] targetKeys = targetKeysStr.split(",");
		if(args.getTargetJson()!=null && !args.getTargetJson().equals("")){
			try {
				List<String>stJsons = getElementsByJsonArr(args.getTargetJson());
				List<String>ndJsons = new ArrayList<>();
				List<String>jsons = new ArrayList<>();
				if(stJsons!=null)
				for(String json:stJsons) ndJsons.add(this.cutByClosedChar(json, '[', ']'));
				if(ndJsons!=null)
				for(String json:ndJsons) jsons.add(this.cutByClosedChar(json, '{', '}'));
				
				List<Map<String,String>> listMaps = new ArrayList<Map<String,String>>();
				if(layerLens==1){
					if(targetKeys!=null && targetKeys.length>=1){
						Map<String,String> targetMap = new HashMap<String,String>();
						for(String targetKey:targetKeys){
							targetMap.put(targetKey, getClosureJson(args.getTargetJson(), targetKey));
						}
						listMaps.add(targetMap);
					}
				}else{
					if(targetKeys!=null && targetKeys.length>=1){
						ndJsons.forEach(jsn->{
							Map<String,String> targetMap = new HashMap<String,String>();
							for(String targetKey:targetKeys){
								targetMap.put(targetKey, getClosureJson(jsn, targetKey));
							}
							listMaps.add(targetMap);
						});
						
					}
				}
				rtMap.put(Constant.MAP_VALUE_KEY, listMaps);
			} catch (Exception e) {
				args.setError(true);
				rtMap.put(Constant.MAP_VALUE_KEY, null);
				return;
			}
		}else{
			rtMap.put(Constant.MAP_VALUE_KEY, null);
		}
	}
	
	

	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonResult#result()
	 */
	@Override
	public Map<String, Object> result() {
		return this.rtMap;
	}
	
}