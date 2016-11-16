/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.parse;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * json 流转对象.
 *
 * @author dk
 */
public class ArgsTransition{
	
	/** The target json. */
	private String targetJson;
	
	/** The results. */
	private List<String> results;
	
	/** The error. */
	private boolean error;
	
	/** The is arr all. */
	private boolean isArrAll;
	
	/** The is arr one. */
	private boolean isArrOne;
	
	/** The arr idx. */
	private int arrIdx;
	
	
	/**
	 * Inits the args.
	 *
	 * @param targetJson the target json
	 */
	public void initArgs(String targetJson){
		this.targetJson = targetJson;
		this.results = null;
		this.error = false;
		this.isArrAll = false;
		this.isArrAll = false;
		this.arrIdx = 0;
	}
	
	/**
	 * Gets the target json.
	 *
	 * @return the target json
	 */
	public String getTargetJson() {
		return targetJson;
	}
	
	/**
	 * Sets the target json.
	 *
	 * @param targetJson the target json
	 */
	public void setTargetJson(String targetJson) {
		this.targetJson = targetJson;
	}
	
	/**
	 * Gets the results.
	 *
	 * @return the results
	 */
	public List<String> getResults() {
		return results;
	}
	
	/**
	 * Sets the results.
	 *
	 * @param results the results
	 */
	public void setResults(List<String> results) {
		this.results = results;
	}
	
	/**
	 * Checks if is error.
	 *
	 * @return true, if checks if is error
	 */
	public boolean isError() {
		return error;
	}
	
	/**
	 * Sets the error.
	 *
	 * @param error the error
	 */
	public void setError(boolean error) {
		this.error = error;
	}
	
	/**
	 * Checks if is arr all.
	 *
	 * @return true, if checks if is arr all
	 */
	public boolean isArrAll() {
		return isArrAll;
	}
	
	/**
	 * Sets the arr all.
	 *
	 * @param isArrAll the arr all
	 */
	public void setArrAll(boolean isArrAll) {
		this.isArrAll = isArrAll;
	}
	
	/**
	 * Checks if is arr one.
	 *
	 * @return true, if checks if is arr one
	 */
	public boolean isArrOne() {
		return isArrOne;
	}
	
	/**
	 * Sets the arr one.
	 *
	 * @param isArrOne the arr one
	 */
	public void setArrOne(boolean isArrOne) {
		this.isArrOne = isArrOne;
	}
	
	/**
	 * Gets the arr idx.
	 *
	 * @return the arr idx
	 */
	public int getArrIdx() {
		return arrIdx;
	}
	
	/**
	 * Sets the arr idx.
	 *
	 * @param arrIdx the arr idx
	 */
	public void setArrIdx(int arrIdx) {
		this.arrIdx = arrIdx;
	}
	
}