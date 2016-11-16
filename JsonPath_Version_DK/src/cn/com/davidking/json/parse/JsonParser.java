/*
 *    功能名称   ： json path实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse;

import java.util.HashMap;
import java.util.Map;

import cn.com.davidking.json.Constant;


// TODO: Auto-generated Javadoc
/**
 * The Class JsonParser.
 */
public class JsonParser  {  
    
    /**
     * Json path.
     *
     * @param json the json
     * @param path the path
     * @return the map< string, object>
     */
    public Map<String,Object> jsonPath(String json,String path){
		String[] namesArr = path.split(Constant.regSeparator);
		int layerLens  = namesArr.length-1;
		
		Map<String,Object> rtMap = new HashMap<String,Object>();

		ArgsTransition args = new ArgsTransition();
		args.initArgs(json);
		
		
		if(namesArr!=null && layerLens>0){
			for(int i=1;i<=layerLens;i++){
				String nodeName = namesArr[i];
				if(nodeName==null ||nodeName.equals("")){
					rtMap =  null;
					break;
				}
				//$.children[0].retired_desc([0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日)
				if(nodeName.matches(Constant.mapSingleVByReg)){
					PickSingleVMatchReg psvmr = 
							new PickSingleVMatchReg(
									layerLens,
									args.getTargetJson(),
									rtMap,
									args.getResults(),
									args.isError(),
									args.isArrAll(),
									args.isArrOne(),
									i,
									nodeName,
									args.getArrIdx()
									);
					psvmr.pick();
					rtMap = psvmr.result();
				}else
				//$.children[0].girls[*].[<name>]  \[<([$_a-zA-Z]{1}[$_a-zA-Z0-9]+,?)+>(<[~!@#$%&-,]+>)?\]
				if(nodeName.matches(Constant.mapStandRegExp)){
					PickStandKeyVal psk = new PickStandKeyVal(layerLens,nodeName,args.getTargetJson(),rtMap);
					psk.pick();
					rtMap = psk.result();
					
				}else if(nodeName.matches(Constant.mapAllRegExp)){
					//bug_fatal_2016_11_09_001  解决$.{name,id} 取不出来
					PickMultiKeyVal mapAllRegExp = new PickMultiKeyVal(layerLens,nodeName,args.getTargetJson(),rtMap);
					mapAllRegExp.pick();
					rtMap = mapAllRegExp.result();
					
				}else if(nodeName.matches(Constant.javaNameRegExp)){
					PickSingleKeyVal javaNameRegExp = 
							new PickSingleKeyVal(
									layerLens,
									args.getTargetJson(),
									rtMap,
									args.getResults(),
									args.isError(),
									args.isArrAll(),
									args.isArrOne(),
									i,
									nodeName,
									args.getArrIdx());
					javaNameRegExp.pick();
					
					rtMap = javaNameRegExp.result();
				}else if(nodeName.matches(Constant.javaArrAllRegExp)){
					PickArrAllVal javaArrAllRegExp = new PickArrAllVal(
							args.isArrAll(),
							nodeName,
							args.getTargetJson(),
							args.isError());
					javaArrAllRegExp.pick();
					args = javaArrAllRegExp.peek();
				}else if(nodeName.matches(Constant.javaArrOneRegExp)){
					PickArrOneVal javaArrOneRegExp = new PickArrOneVal(
							args.isArrOne(),
							args.getArrIdx(),
							nodeName,
							args.getTargetJson(),
							args.isError());
					javaArrOneRegExp.pick();
					args = javaArrOneRegExp.peek();
				}else{
					rtMap =  null;
					break;
				}
				if(args.isError()){
					rtMap =  null;
					break;
				}
				
			}
		}
		return rtMap;
	}
}  











