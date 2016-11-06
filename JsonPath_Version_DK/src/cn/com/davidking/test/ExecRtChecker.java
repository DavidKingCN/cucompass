/*
 *    功能名称   ： json path实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class ExecRtChecker.
 */
public final class ExecRtChecker {

	/**
	 * Check exec rt.
	 *
	 * @param tc
	 *            the tc
	 */
	public static void checkExecRt(TmsCounter tc) {
		tc.setTimes(1).setNeedTotal(true).setNeedAvg(true).calcTms();
	}

	/**
	 * Check exec perf.
	 *
	 * @param tc the tc
	 * @param times the times
	 */
public static void checkExecPerf(List<TmsCounter> tcs,int times){
	Map<TmsCounter,Map<String,String>> perfRts = new HashMap<TmsCounter,Map<String,String>>();
	tcs.forEach(tc->{
		tc
			.setTimes(times)
			.setNeedTotal(false)
			.setNeedAvg(false)
			.calcTms();
		perfRts.put(tc,tc.getExecTms());
	});
		
	perfRts.forEach((tc,perfRt)->{
			
		System.out.print(
				"执行"+tc.getClass().getSimpleName()+" "+times+"次总耗时"+perfRt.get(TmsCounter.TMS_TOTAL)+"毫秒.\n"+
				"执行"+tc.getClass().getSimpleName()+" "+times+"次平均每次耗时"+perfRt.get(TmsCounter.TMS_AVG)+"毫秒.\n"
				);
	});
}}
