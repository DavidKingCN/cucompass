/*
 *    功能名称   ： httpclient 封裝实现1.2
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.http.core;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.FutureRequestExecutionService;

// TODO: Auto-generated Javadoc
/**
 * The Class AsynServices.
 */
public class AsynServices implements ResourceClose {
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(AsynServices.class);
	
	/** The exe service. */
	ExecutorService exeService;// = Executors.newFixedThreadPool(4);
	
	/** The f req exe service. */
	FutureRequestExecutionService fReqExeService;
	
	
	/**
	 * The Constructor.
	 *
	 * @param exeService the exe service
	 * @param fReqExeService the f req exe service
	 */
	public AsynServices(ExecutorService exeService, FutureRequestExecutionService fReqExeService) {
		super();
		this.exeService = exeService;
		this.fReqExeService = fReqExeService;
	}


	/**
	 * The Constructor.
	 */
	public AsynServices() {
		super();
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see cn.com.davidking.http.core.ResourceClose#close()
	 */
	@Override
	public void close() {
		try {
			exeService.shutdown();
			while(!exeService.isTerminated()){
			}
		} catch (Exception e) {
			LOG.error("关闭连接池异常:"+e);
		}finally{
			try {
				if(fReqExeService!=null)
					fReqExeService.close();
			} catch (IOException e) {
				LOG.error("关闭请求连接池异常:"+e);
			}
		}
	}

}
