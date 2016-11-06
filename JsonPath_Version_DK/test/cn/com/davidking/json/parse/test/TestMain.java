/*
 *    功能名称   ： json path实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse.test;

import java.util.ArrayList;
import java.util.List;

import cn.com.davidking.test.ExecRtChecker;
import cn.com.davidking.test.TmsCounter;

// TODO: Auto-generated Javadoc
/**
 * The Class TestMain.
 */
public class TestMain {

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		
		
		//测试执行结果 
//		ExecRtChecker.checkExecRt(new TestJsoupPath());
//		ExecRtChecker.checkExecRt(new TestSlfJsonPath());
		//测试性能
		final int N = 1;
		List<TmsCounter> tcs = new ArrayList<TmsCounter>();
		tcs.add(new TestJsoupPath());
		tcs.add(new TestSlfJsonPath());
		
		ExecRtChecker.checkExecPerf(tcs,N);
	}

}
