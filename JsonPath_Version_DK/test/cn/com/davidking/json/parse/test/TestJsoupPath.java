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
import java.util.List;

import com.jayway.jsonpath.JsonPath;

import cn.com.davidking.json.util.FileUtil;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;

// TODO: Auto-generated Javadoc
/**
 * The Class TestJsoupPath.
 */
public class TestJsoupPath extends MethodExec {

	/* (non-Javadoc)
	 * @see cn.com.davidking.test.ExecProc#process()
	 */
	@Override
	public void process() {
		File jsonFile = new File("resources/json.json");

		String json = FileUtil.nioReadFile(jsonFile.getAbsolutePath());

		JsonPath path = JsonPath.compile("$.children[*].name");

		List<String> names = path.read(json);
		System.out.println("jsoup:" + names);

		String name = JsonPath.read(json, "$.children[0].children[0].name").toString();
		System.out.println("jsoup:" + name);
	}

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		
		ExecChecker.checkExecRt(new TestJsoupPath());	
			
	}
}
