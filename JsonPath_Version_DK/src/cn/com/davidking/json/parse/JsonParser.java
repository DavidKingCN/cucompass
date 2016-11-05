package cn.com.davidking.json.parse;

import java.util.HashMap;
import java.util.Map;

import cn.com.davidking.json.Constant;


public class JsonParser  {  
    
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
				
				if(nodeName.matches(Constant.mapAllRegExp)){
					MapAllRegExp mapAllRegExp = new MapAllRegExp(nodeName,args.getTargetJson(),rtMap);
					mapAllRegExp.jsonPull();
					rtMap = mapAllRegExp.result();
					
				}else if(nodeName.matches(Constant.javaNameRegExp)){
					JavaNameRegExp javaNameRegExp = 
							new JavaNameRegExp(
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
					javaNameRegExp.jsonPull();
					
					rtMap = javaNameRegExp.result();
				}else if(nodeName.matches(Constant.javaArrAllRegExp)){
					JavaArrAllRegExp javaArrAllRegExp = new JavaArrAllRegExp(
							args.isArrAll(),
							nodeName,
							args.getTargetJson(),
							args.isError());
					javaArrAllRegExp.jsonPull();
					args = javaArrAllRegExp.peek();
				}else if(nodeName.matches(Constant.javaArrOneRegExp)){
					JavaArrOneRegExp javaArrOneRegExp = new JavaArrOneRegExp(
							args.isArrOne(),
							args.getArrIdx(),
							nodeName,
							args.getTargetJson(),
							args.isError());
					javaArrOneRegExp.jsonPull();
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











