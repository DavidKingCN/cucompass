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

import cn.com.davidking.json.parse.JsonQuery;
import cn.com.davidking.util.FileUtil;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;

// TODO: Auto-generated Javadoc
/**
 * The Class TestPickSingleVByReg_2016_11_16.
 */
public class TestJsonQuery_Fun_5 extends MethodExec {

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestJsonQuery_Fun_5());
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.test.ExecProc#process()
	 */
	@Override
	public void process() {
		File jsonFile = new File("src/main/resources/json.json");

		String json = FileUtil.nioReadFile(jsonFile.getAbsolutePath());
		
		String retire_year = JsonQuery.getSingleVal(json, "$.children[0].retired_desc([0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日)");
		System.out.println("爸爸退休年龄："+retire_year);
		
		List<String> girlAges = JsonQuery.getListVal(json, "$.children[0].girls[*].age(\\d+)");
		
		for(String age:girlAges){
			System.out.println(age);
		}
	}

}
