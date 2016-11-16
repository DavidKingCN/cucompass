package cn.com.davidking.json.parse.test;

import java.io.File;
import java.util.List;

import cn.com.davidking.json.parse.JsonQuery;
import cn.com.davidking.json.util.FileUtil;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;

public class TestPickSingleVByReg_2016_11_16 extends MethodExec {

	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestPickSingleVByReg_2016_11_16());
	}

	@Override
	public void process() {
		File jsonFile = new File("resources/json.json");

		String json = FileUtil.nioReadFile(jsonFile.getAbsolutePath());
		
		String retire_year = JsonQuery.getSingleVal(json, "$.children[0].retired_desc([0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日)");
		System.out.println("爸爸退休年龄："+retire_year);
		
		List<String> girlAges = JsonQuery.getListVal(json, "$.children[0].girls[*].age(\\d+)");
		
		for(String age:girlAges){
			System.out.println(age);
		}
	}

}
