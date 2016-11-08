/*
 *    功能名称   ： json path实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.test;

import java.util.Map;

import net.sf.cglib.proxy.Enhancer;

// TODO: Auto-generated Javadoc
/**
 * The Class MethodExec.
 *
 */
public abstract class MethodExec implements ExecProc, TmsCounter {

	/** The times. */
	protected int times=1;
	
	/** The need total. */
	protected boolean needTotal=false;
	
	/** The need avg. */
	protected boolean needAvg=false;
	
	/** The exec tms. */
	protected Map<String,String> execTms ;
	
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.test.TmsCounter#calcTms()
	 */
	@Override
	public void calcTms() {
		if(this instanceof ExecProc){
			calcTms(this.times,this.needTotal,this.needAvg,this.getClass());	
		}
	}
	/**
	 * Calc tms.
	 *
	 * @param times the times
	 * @param needTotal the need total
	 * @param needAvg the need avg
	 * @param clazz the clazz
	 */
	private void calcTms(int times,boolean needTotal,boolean needAvg,Class<?> clazz) {
		AutoProxy autoProxy = new AutoProxy(times, needTotal, needAvg);
		
		Enhancer enhancer = new Enhancer();
		
		enhancer.setSuperclass(clazz);
		
		enhancer.setCallback(autoProxy);
		
		ExecProc execTime = (ExecProc)enhancer.create();
		
		execTime.process();
		
		this.execTms = autoProxy.getExecTms();
	}
	
	/**
	 * Gets the times.
	 *
	 * @return the times
	 */
	public int getTimes() {
		return times;
	}
	/* (non-Javadoc)
	 * @see cn.com.davidking.test.TmsCounter#setTimes(int)
	 */
	@Override
	public TmsCounter setTimes(int times) {
		this.times = times;
		return this;
	}
	
	/**
	 * Checks if is need total.
	 *
	 * @return true, if checks if is need total
	 */
	public boolean isNeedTotal() {
		return needTotal;
	}
	/* (non-Javadoc)
	 * @see cn.com.davidking.test.TmsCounter#setNeedTotal(boolean)
	 */
	@Override
	public TmsCounter setNeedTotal(boolean needTotal) {
		this.needTotal = needTotal;
		return this;
	}
	
	/**
	 * Checks if is need avg.
	 *
	 * @return true, if checks if is need avg
	 */
	public boolean isNeedAvg() {
		return needAvg;
	}
	/* (non-Javadoc)
	 * @see cn.com.davidking.test.TmsCounter#setNeedAvg(boolean)
	 */
	@Override
	public TmsCounter setNeedAvg(boolean needAvg) {
		this.needAvg = needAvg;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.test.TmsCounter#getExecTms()
	 */
	@Override
	public Map<String, String> getExecTms() {
		return execTms;
	}
	
	/**
	 * Sets the exec tms.
	 *
	 * @param execTms the exec tms
	 */
	public void setExecTms(Map<String, String> execTms) {
		this.execTms = execTms;
	}
	

	
}
