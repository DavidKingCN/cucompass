package cn.com.davidking.json.parse;

import cn.com.davidking.json.Constant;
import cn.com.davidking.json.util.MatchUtils;

public class JavaArrAllRegExp extends JsonPullerManager implements JsonPuller,JsonTransition{
	
	public JavaArrAllRegExp(){}
	
	public JavaArrAllRegExp(boolean isArrAll,String nodeName,String targetJson,boolean error){
		super(isArrAll,nodeName,targetJson,error);
	}
	@Override
	public void jsonPull() {
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

	@Override
	public ArgsTransition peek() {
		
		ArgsTransition args = new ArgsTransition();
		
		args.setArrAll(this.isArrAll);
		args.setTargetJson(this.targetJson);
		args.setError(this.error);
		return args;
	}
	
}