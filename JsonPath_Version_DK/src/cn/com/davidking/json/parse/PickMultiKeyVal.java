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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.davidking.json.Constant;


// TODO: Auto-generated Javadoc
/**
 * The Class PickMultiKeyVal.
 */
public class PickMultiKeyVal extends JsonPickTools implements JsonPicker,JsonResult{
	
	/**
	 * The Constructor.
	 */
	public PickMultiKeyVal(){}
	
	/**
	 * The Constructor.
	 *
	 * @param nodeName the node name
	 * @param targetJson the target json
	 * @param rtMap the rt map
	 */
	//bug_fatal_2016_11_09_001  解决$.{name,id} 取不出来
	public PickMultiKeyVal(int layerLens,String nodeName,String targetJson,Map<String,Object> rtMap){
		super(layerLens,nodeName,targetJson,rtMap);
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPicker#pick()
	 */
	@Override
	public void pick() {
		String targetKeysStr = nodeName.substring(nodeName.indexOf("{")+1, nodeName.lastIndexOf("}"));
		String[] targetKeys = targetKeysStr.split(",");
		List<String>jsons = getElementsByJsonArr(targetJson);
		
		List<Map<String,String>> listMaps = new ArrayList<Map<String,String>>();
		//bug_fatal_2016_11_09_001  解决$.{name,id} 取不出来
		if(layerLens==1){
			if(targetKeys!=null && targetKeys.length>1){
				Map<String,String> targetMap = new HashMap<String,String>();
				for(String targetKey:targetKeys){
					targetMap.put(targetKey, getClosureJson(targetJson, targetKey));
				}
				listMaps.add(targetMap);
			}
		}else{
			if(targetKeys!=null && targetKeys.length>1){
				jsons.forEach(jsn->{
					Map<String,String> targetMap = new HashMap<String,String>();
					for(String targetKey:targetKeys){
						targetMap.put(targetKey, getClosureJson(jsn, targetKey));
					}
					listMaps.add(targetMap);
				});
				
			}
		}
		rtMap.put(Constant.MAP_VALUE_KEY, listMaps);
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonResult#result()
	 */
	@Override
	public Map<String, Object> result() {
		return this.rtMap;
	}
	
}