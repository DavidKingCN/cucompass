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

import cn.com.davidking.extract.SqlsCreater;
// TODO: Auto-generated Javadoc

/**
 * The Class SqlExecRt.
 *
 * @author daikai
 */
public class SqlExecRt {
	
	/** The sqls creater. */
	private SqlsCreater sqlsCreater;
	
	/** The final over. */
	private boolean finalOver=false;
	
	/** The sk tab. */
	private String skTab;
	
	/** The sql. */
	private String sql;
	
	/** The sql exec rt. */
	private boolean sqlExecRt;
	
	
	private List<Object> colVals;
	
	/**
	 * Exec sql.
	 *
	 * @param skTab the sk tab
	 * @param sql the sql
	 * @return the sql exec rt
	 */
	public SqlExecRt execSql(String skTab,String sql){
		this.skTab = skTab;
		this.sql = sql;
		return this;
	}
	
	public SqlExecRt execSql(String skTab,String sql,List<Object> colVals){
		this.skTab = skTab;
		this.sql = sql;
		this.colVals = colVals;
		return this;
	}
	/**
	 * Final over.
	 *
	 * @param sqlsCreater the sqls creater
	 * @return the sql exec rt
	 */
	public SqlExecRt finalOver(SqlsCreater sqlsCreater){
		this.sqlsCreater = sqlsCreater;
		this.finalOver = true;
		return this;
	}
	
	/**
	 * Gets the sqls creater.
	 *
	 * @return the sqls creater
	 */
	public SqlsCreater getSqlsCreater() {
		return sqlsCreater;
	}
	
	/**
	 * Sets the sqls creater.
	 *
	 * @param sqlsCreater the sqls creater
	 */
	public void setSqlsCreater(SqlsCreater sqlsCreater) {
		this.sqlsCreater = sqlsCreater;
	}
	
	/**
	 * Checks if is final over.
	 *
	 * @return true, if checks if is final over
	 */
	public boolean isFinalOver() {
		return finalOver;
	}
	
	/**
	 * Sets the final over.
	 *
	 * @param finalOver the final over
	 */
	public void setFinalOver(boolean finalOver) {
		this.finalOver = finalOver;
	}
	
	/**
	 * Gets the sk tab.
	 *
	 * @return the sk tab
	 */
	public String getSkTab() {
		return skTab;
	}
	
	/**
	 * Sets the sk tab.
	 *
	 * @param skTab the sk tab
	 */
	public void setSkTab(String skTab) {
		this.skTab = skTab;
	}
	
	/**
	 * Gets the sql.
	 *
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}
	
	/**
	 * Sets the sql.
	 *
	 * @param sql the sql
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	/**
	 * Checks if is sql exec rt.
	 *
	 * @return true, if checks if is sql exec rt
	 */
	public boolean isSqlExecRt() {
		return sqlExecRt;
	}
	
	/**
	 * Sets the sql exec rt.
	 *
	 * @param sqlExecRt the sql exec rt
	 */
	public void setSqlExecRt(boolean sqlExecRt) {
		this.sqlExecRt = sqlExecRt;
	}

	public List<Object> getColVals() {
		return colVals;
	}

	public void setColVals(List<Object> colVals) {
		this.colVals = colVals;
	}
	
	
}
