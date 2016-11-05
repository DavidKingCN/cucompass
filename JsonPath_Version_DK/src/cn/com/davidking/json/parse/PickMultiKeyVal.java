package cn.com.davidking.json.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.davidking.json.Constant;


public class PickMultiKeyVal extends JsonPickTools implements JsonPicker,JsonResult{
	public PickMultiKeyVal(){}
	public PickMultiKeyVal(String nodeName,String targetJson,Map<String,Object> rtMap){
		super(nodeName,targetJson,rtMap);
	}
	@Override
	public void pick() {
		String targetKeysStr = nodeName.substring(nodeName.indexOf("{")+1, nodeName.lastIndexOf("}"));
		String[] targetKeys = targetKeysStr.split(",");
		List<String>jsons = getElementsByJsonArr(targetJson);
		
		List<Map<String,String>> listMaps = new ArrayList<Map<String,String>>();
		if(targetKeys!=null && targetKeys.length>1){
			jsons.forEach(jsn->{
				Map<String,String> targetMap = new HashMap<String,String>();
				for(String targetKey:targetKeys){
					targetMap.put(targetKey, getClosureJson(jsn, targetKey));
				}
				listMaps.add(targetMap);
			});
			
		}
		rtMap.put(Constant.MAP_VALUE_KEY, listMaps);
	}
	@Override
	public Map<String, Object> result() {
		return this.rtMap;
	}
	
}