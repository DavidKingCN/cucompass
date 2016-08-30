/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SuppressWarnings("all")
public class CrawlerMonitor<T> implements Observable {

	private boolean changed = false;
    @SuppressWarnings("unchecked")
    private Vector obs;				
    
    //实例化
	public CrawlerMonitor() {
		super();
		this.obs = new Vector();
	}

	public void addObserver(Observer o) {
		if (o == null)
            throw new NullPointerException("不存在..");
        if (!obs.contains(o)) {
            obs.addElement(o);
            
        }
		
	}
	/* (non-Javadoc)    
     * @see    
     */
    public synchronized void deleteObserver(Observer o) {
        obs.removeElement(o);
    }

    /* (non-Javadoc)    
     * @see
     */
    public void notifyObservers() {
       notifyObservers(null);
    }

    /* (non-Javadoc)    
     * @see  
     */
    @SuppressWarnings("unchecked")
	public void notifyObservers(Object arg) {
        /*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        Object[] arrLocal;

        synchronized (this) {
            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not).  The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             *   notification in progress
             * 2) a recently unregistered Observer will be
             *   wrongly notified when it doesn't care
             */
            if (!changed)
            	return;//
            arrLocal = obs.toArray();
            clearChanged();
        }
        
        
        
        synchronized (this) {
        	for (int i = arrLocal.length-1; i>=0; i--)
	            ((Observer)arrLocal[i]).update(this, arg);
		}
        
    }

    public List exeCallback(){
    	List<T> results = new ArrayList<T>();
        
        synchronized (this) {
	        
	        //做备份
	        ExecutorService executorService = Executors.newCachedThreadPool();
	        //遍历得出结果
	        for(int i = 0 ; i < obs.size(); i++ ){
	        	Observer observer = (Observer)obs.get(i);
	        	Future<Map<Boolean,List<T>>> currFutureResult = 
	        			executorService.submit((Callable<Map<Boolean,List<T>>>)observer);
	        	
	        	try {
	        		Map<Boolean,List<T>> currResult = currFutureResult.get();
	        		if(currResult.containsKey(true)){
	        			List<T> ps = currResult.get(true);
	        			if(ps!=null)
	        				results.addAll(ps);
	        		}
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
		        
	        // TODO 
	        executorService.shutdownNow();
	        // gc collect easier
	        executorService = null;
        }
        return results;
    }
    
    /* (non-Javadoc)    
     * @see   
     */
    
    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    /**
     * Marks this <tt>Observable</tt> object as having been changed; the
     * <tt>hasChanged</tt> method will now return <tt>true</tt>.
     */
    protected synchronized void setChanged() {
        changed = true;
    }

    /**
     * Indicates that this object has no longer changed, or that it has
     * already notified all of its observers of its most recent change,
     * so that the <tt>hasChanged</tt> method will now return <tt>false</tt>.
     * This method is called automatically by the
     * <code>notifyObservers</code> methods.
     *
     * @see     java.util.Observable#notifyObservers()
     * @see     java.util.Observable#notifyObservers(java.lang.Object)
     */
    protected synchronized void clearChanged() {
        changed = false;
    }

    /* (non-Javadoc)    
     * @see  
     */
    public synchronized boolean hasChanged() {
        return changed;
    }

    /* (non-Javadoc)    
     * @see     
     */
    public synchronized int countObservers() {
        return obs.size();
    }




}
