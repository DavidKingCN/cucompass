package cn.com.davidking.json.parse;

import java.util.List;
import java.util.Map;

import cn.com.davidking.json.Constant;


public class PickSingleKeyVal extends JsonPickTools implements JsonPicker,JsonResult{
	public PickSingleKeyVal(){}
	public PickSingleKeyVal(int layerLens, String targetJson, Map<String, Object> rtMap, List<String> results,
			boolean error, boolean isArrAll, boolean isArrOne, int i, String nodeName, int arrIdx){
		super(layerLens,targetJson,rtMap,results,error,isArrAll,isArrOne,i,nodeName,arrIdx);
	}
	@Override
	public void pick() {
		if(isArrAll){
			results = getElementsByJsonArr(targetJson, nodeName);
			if(i==layerLens&&results!=null){
				rtMap.put(Constant.LIST_VALUE_KEY, results);
			}
			
			if(results==null){
				error = true;
			}
		}
		
		if(isArrOne){
			isArrOne = false;arrIdx =0;
		}
		targetJson = getClosureJson(targetJson, nodeName);
		if(i==layerLens&&targetJson!=null){
			rtMap.put(Constant.SINGLE_VALUE_KEY, targetJson);
		}
		
	}
	@Override
	public Map<String, Object> result() {
		return this.rtMap;
	}
	
}