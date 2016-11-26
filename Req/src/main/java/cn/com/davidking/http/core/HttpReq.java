/*
 *    功能名称   ： httpclient 封裝实现1.2
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.http.core;
// TODO: Auto-generated Javadoc

/**
 * The Interface HttpReq.
 */
public interface HttpReq {
	
	/** The Constant SUCCESS. */
	public static final int SUCCESS = 0;

	/** The Constant FAIL. */
	public static final int FAIL = -1;
	
	/**
	 * Check domain valid.
	 *
	 * @return true, if check domain valid
	 */
	public boolean checkDomainValid();
	
	/**
	 * Exec req.
	 *
	 * @param params the params
	 * @return the string
	 */
	public String execReq(Object...params);
	

}
