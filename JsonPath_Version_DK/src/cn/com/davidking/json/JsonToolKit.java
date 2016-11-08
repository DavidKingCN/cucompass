package cn.com.davidking.json;

import java.util.List;

import cn.com.davidking.json.parse.JsonPickTools;

public class JsonToolKit extends JsonPickTools {
	
	public  List<String> getElementsByJsonArr(String jsonArr){
		return super.getElementsByJsonArr(jsonArr);
	}
	
	public  List<String> getElementsByJsonArr(String json,String key){
		return super.getElementsByJsonArr(json, key);
	}
	
	public  String getClosureJson(String json,String key){
		return super.getClosureJson(json, key);
	}
	
	public  String getClosureJson(String json,String key,boolean needPrefix){
		return super.getClosureJson(json, key, needPrefix);
	}
}
