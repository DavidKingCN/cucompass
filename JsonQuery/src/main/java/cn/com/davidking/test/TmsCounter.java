/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.test;
// TODO: Auto-generated Javadoc

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Interface TmsCounter.
 */
public interface TmsCounter {

	/** The TM s_ total. */
	String TMS_TOTAL = "total";
	
	/** The TM s_ avg. */
	String TMS_AVG = "avg";
	/**
	 * Calc tms.
	 */
	void calcTms();

	/**
	 * Sets the times.
	 *
	 * @param times the times
	 * @return the tms counter
	 */
	TmsCounter setTimes(int times);

	/**
	 * Sets the need total.
	 *
	 * @param needTotal the need total
	 * @return the tms counter
	 */
	TmsCounter setNeedTotal(boolean needTotal);

	/**
	 * Sets the need avg.
	 *
	 * @param needAvg the need avg
	 * @return the tms counter
	 */
	TmsCounter setNeedAvg(boolean needAvg);

	/**
	 * Gets the exec tms.
	 *
	 * @return the exec tms
	 */
	Map<String, String> getExecTms();


}