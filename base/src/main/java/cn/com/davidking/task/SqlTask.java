/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.task;

import java.util.concurrent.Callable;

import cn.com.davidking.BeanFactory;

/**
 * The Class SqlTask.
 *
 * @author daikai
 */
public class SqlTask implements Callable<SqlExecRt> {
	
	/** The sql task future rt. */
	private SqlExecRt sqlTaskFutureRt;
	
	/**
	 * The Constructor.
	 */
	public SqlTask() {
		super();
	}

	
	/**
	 * The Constructor.
	 *
	 * @param sqlTaskFutureRt the sql task future rt
	 */
	public SqlTask(SqlExecRt sqlTaskFutureRt) {
		super();
		this.sqlTaskFutureRt = sqlTaskFutureRt;
	}


	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public SqlExecRt call() throws Exception {
		boolean rt = false;
		if(!sqlTaskFutureRt.isFinalOver())
			if(sqlTaskFutureRt.getColVals()!=null)
				rt	= BeanFactory.newPersistence()
					.doExecSql(sqlTaskFutureRt.getSkTab(),sqlTaskFutureRt.getSql(),sqlTaskFutureRt.getColVals());
			else	
//				rt  = BeanFactory.newPersistence()
//							.doExecSql(sqlTaskFutureRt.getSkTab(),sqlTaskFutureRt.getSql());
				rt  = BeanFactory.newPersistence()
				.doExecSqlByDbPool(sqlTaskFutureRt.getSkTab(),sqlTaskFutureRt.getSql());
				
		sqlTaskFutureRt.setSqlExecRt(rt);
		return sqlTaskFutureRt;
	}

	
}
