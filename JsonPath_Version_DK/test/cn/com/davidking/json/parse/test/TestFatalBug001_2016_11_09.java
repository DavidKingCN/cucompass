/*
 *    功能名称   ： json path实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse.test;


import java.util.List;
import java.util.Map;

import cn.com.davidking.JsonToolKitBuilder;
import cn.com.davidking.http.core.HttpTemplate;
import cn.com.davidking.json.parse.JsonQuery;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;
// TODO: Auto-generated Javadoc

/**
 * 测试致命bug001.
 *
 * @author daikai
 */
public class TestFatalBug001_2016_11_09 extends MethodExec {

	/* (non-Javadoc)
	 * @see cn.com.davidking.test.ExecProc#process()
	 */
	@Override
	public void process() {
		String json = HttpTemplate.doGet(
				"http://www.ly.com/hotel/handler/gethotelcomments.json?hotelid=93700&tagid=1", 
				null,
				null,
				null);
		String testJson = JsonToolKitBuilder.newJsonToolKit().getClosureJson(json, "body");
		System.out.println("json val = "+testJson);
		List<Map<String,String>> rs = JsonQuery.getMapVal(testJson, "$.{starNum,goodNum}");
		System.out.println("multi keys vals = "+rs);
	}

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestFatalBug001_2016_11_09());
	}

}
