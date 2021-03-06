/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.davidking.json.Constant;
import cn.com.davidking.util.RegisterUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class JsonParser.
 */
public class JsonParser  {  
	
	/** The pickers. */
	private static Map<String,JsonPicker> pickers = new HashMap<String,JsonPicker>();
	static{	pickers =RegisterUtil.initJsonPickers();}
    
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
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(namesArr!=null && layerLens>0)
			for(int i=1;i<=layerLens;i++){
				String nodeName = namesArr[i];
				if(nodeName==null ||nodeName.equals("")){ rtMap =  null; break;}
				//
				params	.put("layerLens", layerLens);
				params	.put("rtMap", rtMap);
				params	.put("i", i);
				params	.put("nodeName", nodeName);
				params	.put("args", args);
				boolean match = false;
				boolean over = i == layerLens;
				for(Entry<String,JsonPicker> item:pickers.entrySet()){
					String rule 	  = item.getKey();
					JsonPicker picker = item.getValue();
					if(nodeName.matches(rule)){
						match = true; picker.init(params); picker.pick();
						if(over && picker instanceof JsonResult)
							rtMap = ((JsonResult) picker).result();
					}
				}
				if(!match||args.isError()){	rtMap = null; break;}
			}
		
		return rtMap;
	}
}  











