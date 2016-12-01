/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import cn.com.davidking.json.Constant;


// TODO: Auto-generated Javadoc
/**
 * 匹配原则与 [$_a-zA-Z]{1}[$_a-zA-Z0-9]+ 一样为了区分
 * [$_a-zA-Z]{1}[$_a-zA-Z0-9]+->hop 标识为跳转标致正则表达式形式
 * 跳转到执行代码中{{},{}}  
 * The Class PickKeyHop.
 */
public class PickKeyHop extends JsonPickTools implements JsonPicker{
	
	/**
	 * The Constructor.
	 */
	public PickKeyHop(){}
	
	
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
	public PickKeyHop(int layerLens, Map<String, Object> rtMap, int i, String nodeName, ArgsTransition args) {
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

		int endIdx = nodeName.indexOf("->");
		String key = nodeName.substring(0, endIdx);
		
		if(args.getTargetJson()==null ||args.getTargetJson().equals("")){
			args.setError(true);return;
		}
		if(i>=layerLens){ 
			args.setError(true);return;
		}
		
		if(args.isArrAll()){
			//#;#
			try {
				List<String> matchJsons = getElementsByJsonArr(args.getTargetJson(), key);
				String aimStr = matchJsons.stream().map(String::toString).collect(Collectors.joining(MARK_SEP));
				args.setTargetJson(aimStr);
			} catch (Exception e) {
				args.setError(true);return;
			}
		}
		
		/*if(args.isArrOne()){
			args.setArrOne(false);
			args.setArrIdx(0);
		}
		args.setTargetJson(getClosureJson(args.getTargetJson(), nodeName));
		if(i==layerLens&&args.getTargetJson()!=null){
			rtMap.put(Constant.SINGLE_VALUE_KEY, args.getTargetJson());
		}*/
		
	}


//	@Override
//	public ArgsTransition peek() {
//		return args;
//	}
	
	
	public static void main(String[] args) {
		String testStr = "dafefafa11232$we->hop";
		boolean rt = testStr.matches(Constant.KEYHOP);
		
		System.out.println(rt);
		
		int endIdx = testStr.indexOf("->");
		String rtStr = testStr.substring(0, endIdx);
		String hop   = testStr.substring(endIdx+2);
		System.out.println(rtStr);
		
		System.out.println(hop);
	}
}