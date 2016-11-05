package cn.com.davidking.json.parse;

import cn.com.davidking.json.Constant;
import cn.com.davidking.json.util.MatchUtils;

public class JavaArrOneRegExp extends JsonPullerManager implements JsonPuller,JsonTransition{
	
	public JavaArrOneRegExp(){
		super();
	}
	
	public JavaArrOneRegExp(boolean isArrOne,int arrIdx,String nodeName,String targetJson,boolean error){
		super(isArrOne,arrIdx,nodeName,targetJson,error);
	}
	@Override
	public void jsonPull() {
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
