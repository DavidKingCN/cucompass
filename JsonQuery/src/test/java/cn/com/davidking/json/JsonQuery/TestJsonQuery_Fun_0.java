/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.JsonQuery;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.com.davidking.json.parse.JsonQuery;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;
import cn.com.davidking.util.FileUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class TestJsonPath.
 */
public class TestJsonQuery_Fun_0 extends MethodExec {

	/* (non-Javadoc)
	 * @see cn.com.davidking.test.ExecProc#process()
	 */
	@Override
	public void process() {

		File jsonFile = new File("src/main/resources/json.json");

		String json = FileUtil.nioReadFile(jsonFile.getAbsolutePath());
		List<String> names = JsonQuery.getListVal(json, "$.children[0].children[*].name");
		System.out.println("self:" + names);
		String name = JsonQuery.getSingleVal(json, "$.children[0].children[1].id");
		System.out.println("self:" + name);

		List<Map<String, String>> maps = JsonQuery.getMapVal(json, "$.children[0].children[*].{name,id}");
		System.out.println("self:" + maps);
	}

	
	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		
		ExecChecker.checkExecRt(new TestJsonQuery_Fun_0());
	}
}
