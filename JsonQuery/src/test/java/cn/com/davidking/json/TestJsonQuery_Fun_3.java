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
import java.util.List;
import java.util.Map;

import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;
import cn.com.davidking.util.FileUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class TestMapVals.
 */
public class TestJsonQuery_Fun_3 extends MethodExec {

	/* (non-Javadoc)
	 * @see cn.com.davidking.test.ExecProc#process()
	 */
	@Override
	public void process() {
		File jsonFile = new File("src/main/resources/json.json");

		String json = FileUtil.nioReadFile(jsonFile.getAbsolutePath());

		/*String age = JsonQuery.getSingleVal(json, "$.children[0].children[0].age");
		System.out.println("age="+age);
		
		String hobby = JsonQuery.getSingleVal(json, "$.children[0].children[0].hobby");
		
		System.out.println("hobby="+hobby);
		
		
		String name = JsonQuery.getSingleVal(json, "$.children[0].name");
		
		System.out.println("name="+name);*/
		List<Map<String,String>> rt = JsonQuery.getMapVal(json, "$.children[*].{name}");
		rt.forEach(r->{
			r.forEach((k,v)->{
				System.out.println(k+"="+v);
			});
		});
		
		
		
	}

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestJsonQuery_Fun_3());
	}

}
