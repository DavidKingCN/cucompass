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
 * The Class PickArrOneVal.
 */
public class PickArrOneVal extends JsonPickTools implements JsonPicker,JsonTransition{
	
	/**
	 * The Constructor.
	 */
	public PickArrOneVal(){
		super();
	}
	
	/**
	 * The Constructor.
	 *
	 * @param isArrOne the is arr one
	 * @param arrIdx the arr idx
	 * @param nodeName the node name
	 * @param targetJson the target json
	 * @param error the error
	 */
	public PickArrOneVal(boolean isArrOne,int arrIdx,String nodeName,String targetJson,boolean error){
		super(isArrOne,arrIdx,nodeName,targetJson,error);
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonPicker#pick()
	 */
	@Override
	public void pick() {
		arrIdx = Integer.parseInt(MatchUtils.getSingleValByReg(nodeName, Constant.arrOneRegExp));
		
		isArrOne = true;
		
		String key = MatchUtils.getOnlyMatchs(nodeName, Constant.javaNameRegExp);
		
		targetJson = getClosureJson(targetJson, key);
		if(targetJson!=null&&!targetJson.matches(Constant.jsonEmptyArrRegExp)){
			targetJson = getElementsByJsonArr(targetJson).get(arrIdx);
		} else{
			error = true;
		}
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.json.parse.JsonTransition#peek()
	 */
	@Override
	public ArgsTransition peek() {
		
		ArgsTransition args = new ArgsTransition();
		
		args.setArrOne(this.isArrOne);
		args.setArrIdx(this.arrIdx);
		args.setTargetJson(this.targetJson);
		args.setError(this.isError());
		
		return args;
	}
	
}
