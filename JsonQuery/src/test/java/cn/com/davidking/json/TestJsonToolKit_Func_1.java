/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json;

import java.io.File;

import cn.com.davidking.JsonParserBuilder;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;
import cn.com.davidking.util.FileUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class TestJsonToolKit.
 */
public class TestJsonToolKit_Func_1 extends MethodExec {

	/* (non-Javadoc)
	 * @see cn.com.davidking.test.ExecProc#process()
	 */
	@Override
	public void process() {
		
		File jsonFile = new File("src/main/resources/json.json");

		String json = FileUtil.nioReadFile(jsonFile.getAbsolutePath());
		System.out.println(JsonParserBuilder.newJsonToolKit().getClosureJson(json, "children"));
	}

	
	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestJsonToolKit_Func_1());
	}
}
