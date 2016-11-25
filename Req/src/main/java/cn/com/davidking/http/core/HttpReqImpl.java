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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpStatus;

import cn.com.davidking.http.JsonValidator;
import cn.com.davidking.http.MatchUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class HttpReqImpl.
 *
 * @author daikai
 * @version 1.0
 */
@SuppressWarnings("all")
public class HttpReqImpl extends AbsReq implements HttpReq {
	
	/** The Constant logger. */
	private static final Log LOG = LogFactory.getLog(HttpReqImpl.class);
	
	/** The response. */
	HttpResponse response;
	
	/** The Constant READ_BUFFER. */
	private static final int READ_BUFFER = 1024;
	
	/**
	 * The Constructor.
	 */
	public HttpReqImpl() {
		super();
	}
	
	/**
	 * The Constructor.
	 *
	 * @param url the url
	 * @param methodName the method name
	 */
	public HttpReqImpl(String url, String methodName) {
		super(url, methodName);
	}
	
	
	/* (non-Javadoc)
	 * @see cn.wetime.http.core.WtRequest#exeRequest(java.lang.Object[])
	 */
	public synchronized String execReq(Object...params){
		if(!super.checkDomainValid())
			return String.valueOf(FAIL);
		defaultSet();
		//处理请求头
		processRqHeaders(params);
		//设置cookiestore
		setCookieStore();
		//处理请求参数
		processRqArgs(params);
		//执行请求
		boolean normal = execRq();
		if(!normal)
			return String.valueOf(FAIL);
		//处理响应头
		processRespHeaders(params);
		//处理响应头
		return processRespBody();
	}
	
	/**
	 * Default set.
	 */
	private void defaultSet(){
		//链接管理器
		setConnectionManager();
		//路由计划
		setRoutePlanner();
		//链接存活策略
		setConnKeepAliveStrategy();
		//请求配置
		setRqConf();
		//请求重试处理
		setRqRetry();
	}
	
	/**
	 * Exec rq.
	 *
	 * @return true, if exec rq
	 */
	private boolean execRq(){
		boolean normal = true;
		request = reqBuilder.build();
		
		httpclient= httpClientBuilder.build();
		
		try {
			response = httpclient.execute(request);
		} catch (ClientProtocolException e) {
			LOG.error("客户端协议异常!");
			normal = false;
		} catch (IOException e) {
			LOG.error("读写异常!");
			normal = false;
		}
		return normal;
	}

	/**
	 * Process resp headers.
	 *
	 * @param params the params
	 */
	private void processRespHeaders(Object...params){
		/**
		 * 处理响应头
		 */
		if(params != null && params[2] != null){
			List<Header> respHeaders = (List<Header>)params[2];
			for(Header header:respHeaders){
				response.addHeader(header);
			}
		}
	}
	
	/**
	 * Process resp body.
	 *
	 * @return the string
	 */
	private String processRespBody(){
		/**
		 * 查看相应状态码是否为200
		 * 是则继续
		 * 否则返回失败
		 */
		if(validateHttpCode(response)==FAIL){
			return String.valueOf(FAIL);
		}
		
		/**
		 * 查看返回内容
		 * 为空则返回失败
		 * 不为空则返回内容
		 */
		String content = getResponseContent(response);
		
		if(content!=null&&!content.isEmpty())
			return content;
		
		return String.valueOf(FAIL);
	}
	/**
	 * Gets the method name.
	 *
	 * @return the method name
	 */
	public String getMethodName() {
		return methodName;
	}


	/**
	 * Sets the method name.
	 *
	 * @param methodName the method name
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	/**
	 * 处理由API端响应的httpcode.
	 *
	 * @param response API端向服务器端发起的响应头
	 * @return the int
	 */
	private int validateHttpCode(HttpResponse response){
		//根据返回的http_code进行验证判断
		int statuscode = response.getStatusLine().getStatusCode();
		if(statuscode == HttpStatus.OK.value()){
			return SUCCESS;
		}
		
		return FAIL;
	}
	
	/**
	 * 获取API服务器的响应内容.
	 *
	 * @param response the response
	 * @return the api response content
	 */
	public byte[] getApiResponseContent(HttpResponse response){
		// 获取响应内容
		InputStream inStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HttpEntity responseEntity = response.getEntity();
		try {
			inStream = responseEntity.getContent();
			byte[] buffer = new byte[READ_BUFFER];
			int len = 0;
			StringBuilder result = new StringBuilder();
			while((len = inStream.read(buffer)) != -1){
				baos.write(buffer, 0,len);
				result.append(new String(buffer,0,len, CHARSET_UTF8));
			}
			return baos.toByteArray();
		} catch (IllegalStateException e) {
			LOG.error("",e);
		} catch (IOException e) {
			LOG.error("",e);
		}finally{
			IOUtils.closeQuietly(inStream);
			IOUtils.closeQuietly(baos);
		}
		return new byte[]{};
	}
	
	/**
	 * Sets the connection manager.
	 */
	private void setConnectionManager(){
		//..1设置connection manager...
		HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		httpClientBuilder.setConnectionManager(cm);
	}
	
	/**
	 * Sets the route planner.
	 */
	private void setRoutePlanner(){
		//..2设置代理服务器
		SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(
		        ProxySelector.getDefault());
		httpClientBuilder.setRoutePlanner(routePlanner);
	}
	
	/**
	 * Sets the conn keep alive strategy.
	 */
	private void setConnKeepAliveStrategy(){
		//..3设置链接存活时间
		ConnectionKeepAliveStrategy aliveStrat = new DefaultConnectionKeepAliveStrategy() {

		    @Override
		    public long getKeepAliveDuration(
		            HttpResponse response,
		            HttpContext context) {
		        long keepAlive = super.getKeepAliveDuration(response, context);
		        if (keepAlive == -1) {
		            // Keep connections alive 5 seconds if a keep-alive value
		            // has not be explicitly set by the server
		            keepAlive = 5000;
		        }
		        return keepAlive;
		    }

		};
		
		httpClientBuilder.setKeepAliveStrategy(aliveStrat);
	}

	/**
	 * Sets the rq conf.
	 */
	private void setRqConf(){
		//..4请求的链接超时时间和 套接字读写超时时间
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(2000)
		        .setConnectTimeout(2000)
		        .build();
		
		httpClientBuilder.setDefaultRequestConfig(requestConfig);
	}
	
	/**
	 * Sets the rq retry.
	 */
	private void setRqRetry(){
		//..5设置request 重试策略
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {

		    public boolean retryRequest(
		            IOException exception,
		            int executionCount,
		            HttpContext context) {
		        if (executionCount >= 5) {
		            // Do not retry if over max retry count
		            return false;
		        }
		        if (exception instanceof InterruptedIOException) {
		            // Timeout
		            return false;
		        }
		        if (exception instanceof UnknownHostException) {
		            // Unknown host
		            return false;
		        }
		        if (exception instanceof ConnectTimeoutException) {
		            // Connection refused
		            return false;
		        }
		        if (exception instanceof SSLException) {
		            // SSL handshake exception
		            return false;
		        }
		        HttpClientContext clientContext = HttpClientContext.adapt(context);
		        HttpRequest request = clientContext.getRequest();
		        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
		        if (idempotent) {
		            // Retry if the request is considered idempotent
		            return true;
		        }
		        return false;
		    }

		};
		
		httpClientBuilder.setRetryHandler(retryHandler);
	}
	
	
}
