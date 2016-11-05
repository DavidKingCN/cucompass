/*
 *    功能名称   ： json path实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse.test;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.davidking.json.parse.JsonQuery;
import cn.com.davidking.json.util.FileUtil;



// TODO: Auto-generated Javadoc
/**
 * The Class TestJsonPath.
 */
public class TestJsonPath {

	/**
	 * The main method.
	 *
	 * @param args the args
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception{
		int  times = 1;
		long slfTm  = 0;
		int timesCursor = 1;
		slfTm += testSlfJsonPath(times);
		System.out.println("自己的用时："+slfTm/timesCursor);
		
	}
	
	
	/**
	 * Test slf json path.
	 *
	 * @param times the times
	 * @return the long
	 */
	public static long testSlfJsonPath(int times){
		long bgTm = new Date().getTime();
		File jsonFile = new File("resources/json.json");
		
		String json = FileUtil.nioReadFile(jsonFile.getAbsolutePath());
		
		for(int i=0;i<times;i++){
			
			//$.children.children.name[*]  $.children[0].children[*].{id,name}
			List<String> names = JsonQuery.getListVal(json, "$.children[0].children[*].name");
			System.out.println("self:"+names);
			//$.children.children.name[1]
			String name = JsonQuery.getSingleVal(json, "$.children[0].children[1].id");
			System.out.println("self:"+name);
			
			List<Map<String,String>> maps = JsonQuery.getMapVal(json, "$.children[0].children[*].{name,id}");
			System.out.println("self:"+maps);
			
		}
		long endTm = new Date().getTime();
		
		return endTm-bgTm;
	}
	
	
}
