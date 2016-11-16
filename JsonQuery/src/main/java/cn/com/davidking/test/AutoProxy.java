/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.test;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;  
  
  
// TODO: Auto-generated Javadoc
/**
 * The Class AutoProxy.
 */
public class AutoProxy implements MethodInterceptor { 
	
	/** The times. */
	private int times=1;		//执行次数
	
	/** The need total. */
	private boolean needTotal=false; 	//是否需要总时间
	
	/** The need avg. */
	private boolean needAvg=false;	//是否需要平均时间
	
	/** The exec tms. */
	private Map<String,String> execTms ;
	
	/** The lock. */
	private Object lock = new Object();
	
    /**
     * The Constructor.
     */
    public AutoProxy() {
		super();
	}

	/**
	 * The Constructor.
	 *
	 * @param times the times
	 * @param needTotal the need total
	 * @param needAvg the need avg
	 */
	public AutoProxy(int times, boolean needTotal, boolean needAvg) {
		super();
		this.times = times;
		this.needTotal = needTotal;
		this.needAvg = needAvg;
	}

	/* (non-Javadoc)
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
	 */
//	@Override  
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		
		Object o1 =null;
		
		long bgTm = new Date().getTime();
		
		boolean exeOK = true;
		try {
			o1 = methodProxy.invokeSuper(o, args);
		} catch (Exception e) {
			exeOK = false;
		}
		if(exeOK)
			for(int i=1;i<times;i++)
				methodProxy.invokeSuper(o, args);
		
		
		if(exeOK){
			long endTm = new Date().getTime();
			
			long totalTms = endTm - bgTm;
			long avgTms = totalTms/times;
			synchronized(lock){
				execTms = new HashMap<String,String>();
				execTms.put(TmsCounter.TMS_TOTAL, totalTms+"");
				execTms.put(TmsCounter.TMS_AVG, avgTms+"");
			}
			if(needTotal)
				System.out.println("执行"+method.getDeclaringClass().getSimpleName()+"."+method.getName()+"() "+times+"次总耗时："+totalTms+"毫秒数！");
			if(needAvg)
				System.out.println("执行"+method.getDeclaringClass().getSimpleName()+"."+method.getName()+"() "+"单次平均耗时："+avgTms+"毫秒数！");
			
		}else{
			System.out.println("执行失败...");
		}
        return o1;  
    }


	/**
	 * Gets the times.
	 *
	 * @return the times
	 */
	public int getTimes() {
		return times;
	}


	/**
	 * Sets the times.
	 *
	 * @param times the times
	 */
	public void setTimes(int times) {
		this.times = times;
	}


	/**
	 * Checks if is need total.
	 *
	 * @return true, if checks if is need total
	 */
	public boolean isNeedTotal() {
		return needTotal;
	}


	/**
	 * Sets the need total.
	 *
	 * @param needTotal the need total
	 */
	public void setNeedTotal(boolean needTotal) {
		this.needTotal = needTotal;
	}


	/**
	 * Checks if is need avg.
	 *
	 * @return true, if checks if is need avg
	 */
	public boolean isNeedAvg() {
		return needAvg;
	}


	/**
	 * Sets the need avg.
	 *
	 * @param needAvg the need avg
	 */
	public void setNeedAvg(boolean needAvg) {
		this.needAvg = needAvg;
	}

	/**
	 * Gets the exec tms.
	 *
	 * @return the exec tms
	 */
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