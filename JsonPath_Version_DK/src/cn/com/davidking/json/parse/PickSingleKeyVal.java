/*
 *    功能名称   ： json path实现1.0
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
 * The Class PickSingleKeyVal.
 */
public class PickSingleKeyVal extends JsonPickTools implements JsonPicker,JsonResult{
	
	/**
	 * The Constructor.
	 */
	public PickSingleKeyVal(){}
	
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
	public PickSingleKeyVal(int layerLens, String targetJson, Map<String, Object> rtMap, List<String> results,
			boolean error, boolean isArrAll, boolean isArrOne, int i, String nodeName, int arrIdx){
		super(layerLens,targetJson,rtMap,results,error,isArrAll,isArrOne,i,nodeName,arrIdx);
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPicker#pick()
	 */
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
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonResult#result()
	 */
	@Override
	public Map<String, Object> result() {
		return this.rtMap;
	}
	
}