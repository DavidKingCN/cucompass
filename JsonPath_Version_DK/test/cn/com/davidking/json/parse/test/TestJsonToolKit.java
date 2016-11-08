package cn.com.davidking.json.parse.test;

import java.io.File;


import cn.com.davidking.BeanFactory;
import cn.com.davidking.json.util.FileUtil;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;

public class TestJsonToolKit extends MethodExec {

	@Override
	public void process() {
		
		File jsonFile = new File("resources/json.json");

		String json = FileUtil.nioReadFile(jsonFile.getAbsolutePath());
		System.out.println(BeanFactory.newJsonToolKit().getClosureJson(json, "children"));
	}

	
	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestJsonToolKit());
	}
}
