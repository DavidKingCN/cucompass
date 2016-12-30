/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking;

import java.util.List;
import java.util.concurrent.ExecutorService;

import cn.com.davidking.extract.SqlsCreater;
import cn.com.davidking.task.SqlTaskJob;

// TODO: Auto-generated Javadoc
/**
 * 执行消息队列内的sql.
 *
 * @author
 */
public class SqlExecEngine {
	
	/** The sql exec engine. */
	private static SqlExecEngine sqlExecEngine = new SqlExecEngine();
	
	/** The creaters. */
	List<SqlsCreater> creaters;
	
	/** The boss. */
	ExecutorService boss;
	
	/** The workers. */
	ExecutorService workers;
	
	/**
	 * The Constructor.
	 */
	private SqlExecEngine(){}
	
	/**
	 * New instance.
	 *
	 * @return the sql exec engine
	 */
	public static SqlExecEngine newInstance(){
		if(sqlExecEngine==null)
			sqlExecEngine = new SqlExecEngine();
		return sqlExecEngine;
	}
	
	/**
	 * Inits the.
	 *
	 * @param boss the boss
	 * @param workers the workers
	 * @param creaters the creaters
	 * @return the sql exec engine
	 */
	public SqlExecEngine init(ExecutorService boss,ExecutorService workers,List<SqlsCreater> creaters){
		this.boss = boss;
		this.workers = workers;
		this.creaters = creaters;
		return this;
	}
	
	/**
	 * Asyn exec sql.
	 */
	public void asynExecSql(){
		
		boss.execute(new SqlTaskJob(workers,creaters));
		boss.shutdown();
		while(!boss.isTerminated()){
		}
	}
	
}
