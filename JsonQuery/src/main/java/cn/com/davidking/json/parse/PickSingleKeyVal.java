/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse;

import java.util.Map;

import cn.com.davidking.json.Constant;


// TODO: Auto-generated Javadoc
/**
 * The Class PickSingleKeyVal.
 */
public class PickSingleKeyVal extends JsonPickTools implements PickResult{
	
	/**
	 * The Constructor.
	 */
	public PickSingleKeyVal(){}
	
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPicker#pick()
	 */
	/**
	 * The Constructor.
	 *
	 * @param layerLens the layer lens
	 * @param rtMap the rt map
	 * @param i the i
	 * @param nodeName the node name
	 * @param args the args
	 */
	public PickSingleKeyVal(int layerLens, Map<String, Object> rtMap, int i, String nodeName, ArgsTransition args) {
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
		if(args.isArrAll()){
			try {
				args.setResults(getElementsByJsonArr(args.getTargetJson(), nodeName));
			} catch (Exception e) {
				args.setError(true);
				return;
			}
			if(i==layerLens&&args.getResults()!=null){
				rtMap.put(Constant.LIST_VALUE_KEY, args.getResults());
			}
			
			if(args.getResults()==null){
				args.setError(true);return;
			}
		}
		
		if(args.isArrOne()){
			args.setArrOne(false);
			args.setArrIdx(0);
		}
		
		if(args.getTargetJson()==null||args.getTargetJson().equals("")){
			rtMap.put(Constant.SINGLE_VALUE_KEY, "");
			return;
		}
//		String proJson = cutByClosedChar(args.getTargetJson(), '[', ']');
//		proJson = cutByClosedChar(args.getTargetJson(), '{', '}');
		
		if(i==layerLens){
			args.setTargetJson(getClosureJson(cutAll(args.getTargetJson()), nodeName));
			rtMap.put(Constant.SINGLE_VALUE_KEY, args.getTargetJson());
		}else
			args.setTargetJson(getClosureJson(args.getTargetJson(), nodeName));
	}
	
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonResult#result()
	 */
	@Override
	public Map<String, Object> result() {
		return this.rtMap;
	}
	
}