package cn.com.davidking.json.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.davidking.json.Constant;
import cn.com.davidking.json.util.MatchUtils;
/**
 * $.price(\\d+)根据括号内的正则表达式搜索符合规则的数据，不存在则返回一个指定的默认值
 * @author Administrator
 *
 */
public class PickSingleVMatchReg extends JsonPickTools implements JsonPicker, JsonResult {
	
	

	public PickSingleVMatchReg() {
		super();
	}

	public PickSingleVMatchReg(int layerLens, String targetJson, Map<String, Object> rtMap, List<String> results,
			boolean error, boolean isArrAll, boolean isArrOne, int i, String nodeName, int arrIdx) {
		super(layerLens, targetJson, rtMap, results, error, isArrAll, isArrOne, i, nodeName, arrIdx);
	}

	@Override
	public void pick() {
		String nodeName_ = nodeName.substring(0, nodeName.indexOf("("));
		String reg = nodeName.substring(nodeName.indexOf("(")+1, nodeName.lastIndexOf(")"));
		if(reg==null|| reg.equals("")){
			rtMap.put(Constant.LIST_VALUE_KEY, null);
		}
		if(isArrAll){
			
			results = getElementsByJsonArr(targetJson, nodeName_);
			if(i==layerLens&&results!=null){
				List<String> regRts = new ArrayList<String>();
				for(String rt:results){
					regRts.add(MatchUtils.getOnlyMatchs(rt, reg));
				}
				rtMap.put(Constant.LIST_VALUE_KEY, regRts);
			}else {
				rtMap.put(Constant.LIST_VALUE_KEY, null);
				return;
			}
			
		}
		
		if(isArrOne){
			isArrOne = false;arrIdx =0;
		}
		targetJson = getClosureJson(targetJson, nodeName_);
		if(i==layerLens&&targetJson!=null){
			rtMap.put(Constant.SINGLE_VALUE_KEY, MatchUtils.getOnlyMatchs(targetJson,reg));
		}else{
			rtMap.put(Constant.LIST_VALUE_KEY, null);
		}
	}
	
	@Override
	public Map<String, Object> result() {
		return this.rtMap;
	}
}
