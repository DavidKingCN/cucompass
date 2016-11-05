/*
 *    功能名称   ： json path实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.test;

import net.sf.cglib.proxy.Enhancer;

// TODO: Auto-generated Javadoc
/**
 * The Class MethodExec.
 */
public abstract class MethodExec implements ExecProc {


	/**
	 * Calc tms.
	 *
	 * @param times the times
	 * @param needTotal the need total
	 * @param needAvg the need avg
	 * @param clazz the clazz
	 */
	public void calcTms(int times,boolean needTotal,boolean needAvg,Class<?> clazz) {
		AutoProxy autoProxy = new AutoProxy(times, needTotal, needAvg);
		
		Enhancer enhancer = new Enhancer();
		
		enhancer.setSuperclass(clazz);
		
		enhancer.setCallback(autoProxy);
		
		ExecProc execTime = (ExecProc)enhancer.create();
		
		execTime.process();
	}
}
