/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.task;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import cn.com.davidking.extract.SqlsCreater;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;

// TODO: Auto-generated Javadoc
/**
 * The Class SqlTaskJob.
 */
public class SqlTaskJob implements Runnable {
	
	/** The workers. */
	ExecutorService workers;
	
	/** The creaters. */
	List<SqlsCreater> creaters;
	
	/** The c rt. */
	ConcurrentMap<SqlsCreater, Boolean> cRt = new ConcurrentHashMap<>();
	
	/** The all over. */
	public static boolean allOver;
	
	/** The len. */
	private int len = 0;
	
	/**
	 * The Constructor.
	 */
	public SqlTaskJob() {
		super();
	}

	/**
	 * The Constructor.
	 *
	 * @param workers the workers
	 * @param creaters the creaters
	 */
	public SqlTaskJob(ExecutorService workers,List<SqlsCreater> creaters) {
		super();
		this.workers = workers;
		this.creaters = creaters;
		for(SqlsCreater sc:creaters){
			cRt.put(sc, false);
		}
		len = creaters.size();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Future<SqlExecRt> fr = null;
		int total = 0;
		do{	
			SqlTask sqlTask = SqlTaskSink.getInstance().outflow();
			fr = workers.submit(sqlTask);
			//TODO 分析处理结果，并能把消息传递给另外sql生成器，连续报错超过设定次数停止其线程执行，
			try {
				SqlExecRt r = fr.get();
				if(r!=null){
					if(r.isFinalOver()){
						total++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			if(total >= len){
				if(AsynReqImpl.exeService!=null)
					new AsynServices(AsynReqImpl.exeService,AsynReqImpl.getfReqExeService()).close();
				break;
				
			}
		}while(true);
				
		workers.shutdown();
		while(!workers.isTerminated()){
		}
		System.out.println("工厂关门，工人下班了");
		
	}

	/**
	 * Gets the workers.
	 *
	 * @return the workers
	 */
	public ExecutorService getWorkers() {
		return workers;
	}

	/**
	 * Sets the workers.
	 *
	 * @param workers the workers
	 */
	public void setWorkers(ExecutorService workers) {
		this.workers = workers;
	}

}
