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
			List<String>jsons_ = getElementsByJsonArr(args.getTargetJson());
			List<String>jsons = new ArrayList<>();
			//把这个json中{""}部分找到并赋值为""
			for(String json:jsons_){
				
				String procStr = json.trim();//.substring(json.indexOf("{"), json.lastIndexOf("}"))
				int counter = 1;
				int pos = 1;
				boolean again=true;
				int targetLen = procStr.length();
				int firstPos = -1;
				int endPos = -1;
				Map<Integer,Integer> posNeeds = new HashMap<Integer,Integer>();
				for(int i=1;i<targetLen-1;i++){
					if(procStr.charAt(i)!='\\'&&procStr.charAt(i)=='['){
						if(again){
							firstPos=pos++;again = false;continue;
						}
						counter++;
					} else if(procStr.charAt(i)!='\\'&&procStr.charAt(i)==']')
						counter--;
					pos++;
					if(pos>=targetLen-1)
						break;
					if(counter==0&&procStr.charAt(pos)==','){// 
						endPos = pos;counter = 1;again = true;
						//记忆前后位置就可以了...
						posNeeds.put(firstPos, endPos);
					}
				}
				List<String> rpls = new ArrayList<String>();
				if(posNeeds.size()>0){
					for(Entry<Integer,Integer> posNeed:posNeeds.entrySet()){
						rpls.add(procStr.substring(posNeed.getKey(), posNeed.getValue()));
						//procStr = procStr.replace(procStr.substring(posNeed.getKey(), posNeed.getValue()),"\"\"");
					}
				}
				for(String rpl:rpls){
					if(!rpl.matches("\\[\\s*\\]"))
					procStr = procStr.replace(rpl,"\"\"");
				}
				//System.out.println(procStr);
				jsons.add(procStr);
			}
			
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