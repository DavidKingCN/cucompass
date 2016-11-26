/*
 *    功能名称   ： httpclient 封裝实现1.2
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.http.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.HttpRequestFutureTask;
import org.apache.http.impl.cookie.BasicClientCookie;

import cn.com.davidking.http.JsonValidator;
import cn.com.davidking.http.MatchUtils;
// TODO: Auto-generated Javadoc

/**
 * The Class AsynReqImpl.
 */
@SuppressWarnings("all")
public class AsynReqImpl extends AbsReq implements HttpReq,ResourceClose {
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(AsynReqImpl.class);
	
	/** The result. */
	String result;
	
	//构建线程池
	/** The exe service. */
	ExecutorService exeService = Executors.newFixedThreadPool(4);
	
	/** The f req exe service. */
	FutureRequestExecutionService fReqExeService;
	
	/** The shutdown. */
	boolean shutdown=false;
	/**
	 * The Constructor.
	 */
	public AsynReqImpl() {
		super();
	}

	/**
	 * The Constructor.
	 *
	 * @param url the url
	 * @param methodName the method name
	 */
	public AsynReqImpl(String url,String methodName) {
		super(url, methodName);
	}
	/**
	 * The Constructor.
	 *
	 * @param url the url
	 * @param shutdown the shutdown
	 * @param methodName the method name
	 */
	public AsynReqImpl(String url, boolean shutdown,String methodName) {
		super(url, methodName);
		this.shutdown = shutdown;
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.http.core.HttpReq#execReq(java.lang.Object[])
	 */
	@Override
	public String execReq(Object... params) {
		if(!super.checkDomainValid())
			result = String.valueOf(FAIL);
		processRqHeaders(params);
		//设置cookiestore
		setCookieStore();
		//处理请求参数
		processRqArgs(params);
		execRq();
		if(shutdown) close();
		return result;
	}

	
	/**
	 * Exec rq.
	 *
	 * @return true, if exec rq
	 */
	private boolean execRq(){
		boolean normal = true;
		httpclient= httpClientBuilder.build();
		request = reqBuilder.build();
		
		//构建请求线程池
		if(fReqExeService==null)
			fReqExeService =
			    new FutureRequestExecutionService(httpclient, exeService);
		//执行请求任务并返回执行结果
		HttpRequestFutureTask<ExecRt> task = fReqExeService.execute(
				request, HttpClientContext.create(),
			    new DoRespHandler(), new DoneCallBack());
		
		try {
			ExecRt rt=task.get();
			result = rt.getContent();
		} catch (InterruptedException e1) {
			LOG.error("中断异常:"+e1);
			result = String.valueOf(FAIL);
		} catch (ExecutionException e1) {
			LOG.error("执行异常:"+e1);
			result = String.valueOf(FAIL);
		}
		
		return normal;
	}
	
	/* (non-Javadoc)
	 * @see cn.com.davidking.http.core.ResourceClose#close()
	 */
	public void close(){
		
		try {
			exeService.shutdown();
			while(!exeService.isTerminated()){
			}
		} catch (Exception e) {
			LOG.error("关闭连接池异常:"+e);
		}finally{
			try {
				fReqExeService.close();
			} catch (IOException e) {
				LOG.error("关闭请求连接池异常:"+e);
			}
		}
	}
	/**
	 * The Class DoRespHandler.
	 */
	private  final class DoRespHandler implements ResponseHandler<ExecRt> {
	    
    	/* (non-Javadoc)
    	 * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
    	 */
    	public ExecRt handleResponse(
	            final HttpResponse resp) throws ClientProtocolException, IOException {
	    	ExecRt execRt = new ExecRt();
	    	execRt .setOk(resp.getStatusLine().getStatusCode() == 200);
	    	execRt .setContent(getResponseContent(resp));
	        return execRt;
	    }
	}

	/**
	 * The Class DoneCallBack.
	 */
	private final class DoneCallBack implements FutureCallback<ExecRt> {
	
	    /* (non-Javadoc)
    	 * @see org.apache.http.concurrent.FutureCallback#failed(java.lang.Exception)
    	 */
    	public void failed(final Exception ex) {
	    	System.out.println("执行失败原因："+ex);
	    }
	
	    /* (non-Javadoc)
    	 * @see org.apache.http.concurrent.FutureCallback#completed(java.lang.Object)
    	 */
    	public void completed(final ExecRt result) {
	    	System.out.println(result.isOk()?"请求执行成功!":"请求执行失败!");
	    }
	
	    /* (non-Javadoc)
    	 * @see org.apache.http.concurrent.FutureCallback#cancelled()
    	 */
    	public void cancelled() {
	    	System.out.println("请求执行取消!");
	    }
	}

	/**
	 * The Class ExecRt.
	 */
	private final class ExecRt{
		
		/** The ok. */
		boolean ok;
		
		/** The content. */
		String content;
		
		/**
		 * Checks if is ok.
		 *
		 * @return true, if checks if is ok
		 */
		public boolean isOk() {
			return ok;
		}
		
		/**
		 * Sets the ok.
		 *
		 * @param ok the ok
		 */
		public void setOk(boolean ok) {
			this.ok = ok;
		}
		
		/**
		 * Gets the content.
		 *
		 * @return the content
		 */
		public String getContent() {
			return content;
		}
		
		/**
		 * Sets the content.
		 *
		 * @param content the content
		 */
		public void setContent(String content) {
			this.content = content;
		}
	}

}
