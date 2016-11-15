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


import cn.com.davidking.JsonToolKitBuilder;
import cn.com.davidking.json.util.FileUtil;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;

// TODO: Auto-generated Javadoc
/**
 * The Class TestJsonToolKit.
 */
public class TestJsonToolKit extends MethodExec {

	/* (non-Javadoc)
	 * @see cn.com.davidking.test.ExecProc#process()
	 */
	@Override
	public void process() {
		
		File jsonFile = new File("resources/json.json");

		String json = FileUtil.nioReadFile(jsonFile.getAbsolutePath());
		System.out.println(JsonToolKitBuilder.newJsonToolKit().getClosureJson(json, "children"));
	}

	
	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestJsonToolKit());
	}
}
