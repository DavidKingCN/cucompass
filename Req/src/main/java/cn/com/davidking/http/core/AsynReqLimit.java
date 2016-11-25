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
 * The Class AsynReqLimit.
 */
public class AsynReqLimit extends AbsReq implements HttpReq,ResourceClose{
	
	/** The asyn req impl. */
	private AsynReqImpl asynReqImpl;

	/**
	 * The Constructor.
	 */
	public AsynReqLimit() {
		super();
	}

	/**
	 * The Constructor.
	 *
	 * @param asynReqImpl the asyn req impl
	 */
	public AsynReqLimit(AsynReqImpl asynReqImpl) {
		this.asynReqImpl = asynReqImpl;
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.http.core.AbsReq#checkDomainValid()
	 */
	@Override
	public boolean checkDomainValid() {
		return asynReqImpl.checkDomainValid();
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.http.core.HttpReq#execReq(java.lang.Object[])
	 */
	@Override
	public String execReq(Object... params) {
		asynReqImpl.processRqArgs(params);
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.http.core.ResourceClose#close()
	 */
	@Override
	public void close() {
		
	}

}
