/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.task;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// TODO: Auto-generated Javadoc
/**
 * The Class SqlTaskSink.
 */
public class SqlTaskSink extends LinkedBlockingQueue<SqlTask> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4302444355766271624L;
	
	/** 最大库存量. */
    public static final int Max = 200;
    
    /** 当前库存. */
    public static int Curr = 0;
    
    
    
    /** The lock. */
    ReentrantLock lock = new ReentrantLock();
	
	/** The condition. */
	Condition condition = lock.newCondition();
    
    /** 库存管理实例. */
    private static SqlTaskSink sqlTaskSink;
    
    /**
     * The Constructor.
     */
    private SqlTaskSink(){}
    
    /**
     * 获取单例.
     *
     * @return the instance
     */
    public static SqlTaskSink getInstance(){
        if(sqlTaskSink == null){
        	sqlTaskSink = new SqlTaskSink();
        }
        return sqlTaskSink;
    }
    
    /**
     * 入池操作.
     *
     * @param task the task
     */
    public void inflow(SqlTask task){
    	lock.lock();
    	try{
    		while(Curr>=Max)
    			condition.await();
    		Curr++;
    		System.out.println("入库存量:"+Curr);
    		this.offer(task);
    		condition.signal();
    	}catch(Exception e){
    		
    	}finally{
    		lock.unlock();
    	}
    }
    
    /**
     * 出池操作.
     *
     * @return the sql task
     */
    public SqlTask outflow(){
    	lock.lock();
    	SqlTask task = null;
    	try{
    		while(this.isEmpty())
    			condition.await();
    		task = this.take();
    		Curr--;
    		System.out.println("出库存量:"+Curr);
    		condition.signal();
    	}catch(Exception e){
    		
    	}finally{
    		lock.unlock();
    	}
    	return task;
    }
}
