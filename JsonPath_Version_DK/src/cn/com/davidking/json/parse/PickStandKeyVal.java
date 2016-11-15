package cn.com.davidking.json.parse;

import java.util.List;
import java.util.Map;

import cn.com.davidking.json.Constant;

public class PickStandKeyVal extends JsonPickTools implements JsonPicker, JsonResult {

	
	public PickStandKeyVal() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PickStandKeyVal(int layerLens,String nodeName,String targetJson,Map<String,Object> rtMap){
		super(layerLens,nodeName,targetJson,rtMap);
	}

	@Override
	public void pick() {
		String targetKeysStr = nodeName.substring(nodeName.indexOf("[")+1, nodeName.lastIndexOf("]"));
		String targetKeys_ = targetKeysStr.substring(nodeName.indexOf("<"), nodeName.indexOf(">")-1);
		boolean containSep = nodeName.indexOf("<") != nodeName.lastIndexOf("<");
		String[] seps = null;
		int sepLen = 1;
		String singleSep = Constant.Stand_Separator;
		if(containSep){
			String sepStr = targetKeysStr.substring(nodeName.lastIndexOf("<"), nodeName.lastIndexOf(">")-1);
			sepLen = sepStr.length();
			if(sepLen>1){
				seps = new String[sepLen];
				for(int i=0;i<sepLen;i++){
					seps[i] = sepStr.charAt(i)+"";
				}
			}else{
				singleSep = sepStr;
			}
		}
		String[] targetKeys = null;
		String tk = "";
		if(targetKeys_.contains(",")){
			targetKeys = targetKeys_.split(",");
		}else
			tk = targetKeys_;
		
		if(sepLen>2)
			return;
		
		List<String>jsons = getElementsByJsonArr(targetJson);
		StringBuffer standRt = new StringBuffer();
		if(layerLens==1){
			if(targetKeys!=null && targetKeys.length>=1){
				standRt = multiKey(jsons,targetKeys,seps,sepLen,containSep,singleSep,standRt);
			}else{
				standRt = singleKey(jsons,tk,containSep,singleSep,standRt);
			}
		}else{
			if(targetKeys!=null && targetKeys.length>=1){
				standRt = multiKey(jsons,targetKeys,seps,sepLen,containSep,singleSep,standRt);
				
			}else{
				standRt = singleKey(jsons,tk,containSep,singleSep,standRt);
			}
		}
		rtMap.put(Constant.SINGLE_VALUE_KEY, standRt);
	}

	@Override
	public Map<String, Object> result() {
		return this.rtMap;
	}

	private StringBuffer multiKey(List<String> jsons,String[] targetKeys,String[] seps,int sepLen,boolean containSep,String singleSep,StringBuffer standRt){
		int idx = 0;
		for(String json:jsons){
			int jdx = 0;
			for(String targetKey:targetKeys){
				standRt.append(getClosureJson(json, targetKey));
				if(sepLen==2)
					standRt.append(jdx==targetKeys.length-1?"":seps[0]);
				else if(containSep)
					standRt.append(jdx==targetKeys.length-1?"":singleSep);
				jdx++;
			}
			if(sepLen==2)
				standRt.append(idx==jsons.size()-1?"":seps[1]);
			else if(containSep)
				standRt.append(idx==jsons.size()-1?"":singleSep);
			idx ++;
		}
		return standRt;
	}
	private StringBuffer singleKey(List<String> jsons,String tk,boolean containSep,String sep,StringBuffer standRt){
		int idx = 0;
		for(String json:jsons){
			standRt.append(getClosureJson(json, tk));
			if(!containSep)
				standRt.append(idx++==jsons.size()-1?"":Constant.Stand_Separator);
			else
				standRt.append(sep);
		}
		
		return standRt;
	}
}
