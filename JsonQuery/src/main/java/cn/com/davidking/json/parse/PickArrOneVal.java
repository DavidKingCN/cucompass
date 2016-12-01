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
import cn.com.davidking.util.MatchUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class PickArrOneVal.
 */
public class PickArrOneVal extends JsonPickTools implements JsonPicker{
	
	/**
	 * The Constructor.
	 */
	public PickArrOneVal(){
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
	public PickArrOneVal(int layerLens, Map<String, Object> rtMap, int i, String nodeName, ArgsTransition args) {
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
		args.setArrIdx(Integer.parseInt(MatchUtils.getSingleValByReg(nodeName, Constant.arrOneRegExp)));
		
		args.setArrOne(true);
		
		String key = MatchUtils.getOnlyMatchs(nodeName, Constant.javaNameRegExp);
		
		args.setTargetJson(getClosureJson(args.getTargetJson(), key));
		if(args.getTargetJson()!=null&&!args.getTargetJson().matches(Constant.jsonEmptyArrRegExp)){
			try {
				args.setTargetJson(getElementsByJsonArr(args.getTargetJson()).get(args.getArrIdx()));
			} catch (Exception e) {
				args.setError(true);
			}
		} else{
			args.setError(true);
		}
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonTransition#peek()
	 */
//	@Override
//	public ArgsTransition peek() {
//		return args;
//	}
	
}
