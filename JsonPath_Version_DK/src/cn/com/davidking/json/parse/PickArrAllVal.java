/*
 *    功能名称   ： json path实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse;

import cn.com.davidking.json.Constant;
import cn.com.davidking.json.util.MatchUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class PickArrAllVal.
 */
public class PickArrAllVal extends JsonPickTools implements JsonPicker,JsonTransition{
	
	/**
	 * The Constructor.
	 */
	public PickArrAllVal(){}
	
	/**
	 * The Constructor.
	 *
	 * @param isArrAll the is arr all
	 * @param nodeName the node name
	 * @param targetJson the target json
	 * @param error the error
	 */
	public PickArrAllVal(boolean isArrAll,String nodeName,String targetJson,boolean error){
		super(isArrAll,nodeName,targetJson,error);
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPicker#pick()
	 */
	@Override
	public void pick() {
		isArrAll = true;
		
		String key = MatchUtils.getOnlyMatchs(nodeName, Constant.javaNameRegExp);
		
		if(key==null || key.equals("")){
			error = true;
		}
		targetJson = getClosureJson(targetJson, key);
		
		if(targetJson==null||targetJson.matches(Constant.jsonEmptyArrRegExp)){
			error = true;
		}
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonTransition#peek()
	 */
	@Override
	public ArgsTransition peek() {
		
		ArgsTransition args = new ArgsTransition();
		
		args.setArrAll(this.isArrAll);
		args.setTargetJson(this.targetJson);
		args.setError(this.error);
		return args;
	}
	
}