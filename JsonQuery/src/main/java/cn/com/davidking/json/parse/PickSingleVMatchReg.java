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
import java.util.List;
import java.util.Map;

import cn.com.davidking.json.Constant;
import cn.com.davidking.util.MatchUtils;

// TODO: Auto-generated Javadoc
/**
 * $.price(\\d+)根据括号内的正则表达式搜索符合规则的数据，不存在则返回一个指定的默认值
 * @author Administrator
 *
 */
public class PickSingleVMatchReg extends JsonPickTools implements PickResult {

	/**
	 * The Constructor.
	 */
	public PickSingleVMatchReg() {
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
	public PickSingleVMatchReg(int layerLens, Map<String, Object> rtMap, int i, String nodeName, ArgsTransition args) {
		super(layerLens, rtMap, i, nodeName, args);
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
		String nodeName_ = nodeName.substring(0, nodeName.indexOf("("));
		String reg = nodeName.substring(nodeName.indexOf("(")+1, nodeName.lastIndexOf(")"));
		if(reg==null|| reg.equals("")){
			rtMap.put(Constant.LIST_VALUE_KEY, null);
		}
		if(args.isArrAll()){
			
			args.setResults(getElementsByJsonArr(args.getTargetJson(), nodeName_));
			if(i==layerLens&&args.getResults()!=null){
				List<String> regRts = new ArrayList<String>();
				for(String rt:args.getResults()){
					regRts.add(MatchUtils.getOnlyMatchs(rt, reg));
				}
				rtMap.put(Constant.LIST_VALUE_KEY, regRts);
			}else {
				rtMap.put(Constant.LIST_VALUE_KEY, null);
				return;
			}
			
		}
		
		if(args.isArrOne()){
			args.setArrOne(false); args.setArrIdx(0);
		}
		args.setTargetJson(getClosureJson(args.getTargetJson(), nodeName_));
		if(i==layerLens&&args.getTargetJson()!=null){
			rtMap.put(Constant.SINGLE_VALUE_KEY, MatchUtils.getOnlyMatchs(args.getTargetJson(),reg));
		}else{
			rtMap.put(Constant.LIST_VALUE_KEY, null);
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
