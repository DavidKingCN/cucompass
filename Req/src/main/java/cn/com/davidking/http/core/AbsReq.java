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
import java.util.concurrent.ExecutorService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

import cn.com.davidking.util.JsonValidator;
import cn.com.davidking.util.MatchUtils;
import cn.com.davidking.http.URLTool;

// TODO: Auto-generated Javadoc
/**
 * The Class AbsReq.
 */
@SuppressWarnings("all")
public abstract class AbsReq {
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(AbsReq.class);
	
	
	
	/**
	 * The Constructor.
	 */
	public AbsReq() {
		super();
		httpClientBuilder = HttpClients.custom();
	}

	/**
	 * The Constructor.
	 *
	 * @param url the url
	 * @param methodName the method name
	 */
	public AbsReq(String url, String methodName) {
		this();
		this.url = url;
		this.methodName = methodName;
		setRqBuilder();
	}

	/** The http client builder. */
	protected HttpClientBuilder httpClientBuilder;
	
	/** The req builder. */
	protected RequestBuilder reqBuilder;
	
	/** The cookie store. */
	protected CookieStore cookieStore ;
	
	/** The httpclient. */
	protected HttpClient httpclient;
	
	/** The request. */
	protected HttpUriRequest request;
	
	/** The url. */
	protected String url;
	
	/** The method name. */
	protected String methodName;

	/** The Constant CHARSET. */
	protected static final String CHARSET_UTF8 = Consts.UTF_8.displayName();
	
	public static String charset;
	
	public static void setCharset(String charset){
		AsynReqImpl.charset = charset;
	}
	//构建线程池
	/** The exe service. */
	public static ExecutorService exeService;
	
	public static void setExeService(ExecutorService exeService) {
		AsynReqImpl.exeService = exeService;
	}

	public static  FutureRequestExecutionService getfReqExeService() {
		return fReqExeService;
	}

	/** The f req exe service. */
	static FutureRequestExecutionService fReqExeService;
	/**
	 * Check domain valid.
	 *
	 * @return true, if check domain valid
	 */
	public boolean checkDomainValid(){
		boolean valid = true;
		String domain = URLTool.extDomain(url);
		valid = URLTool.tryConnect(domain);
		return valid;
	}
	/**
	 * Sets the rq builder.
	 */
	protected void setRqBuilder(){
		try {
			reqBuilder = RequestBuilder.create(methodName).setUri(new URI(url));
		} catch (URISyntaxException e) {
			LOG.error("url 不合法！");
		}
	}

	/**
	 * Process rq headers.
	 *
	 * @param params the params
	 */
	protected void processRqHeaders(Object...params){
		/**
		 * 处理请求头
		 */
		if(params!=null&&params[0]!=null){
			List<Header> headers = (List<Header>)params[0];
			
			for(Header header:headers){
				String name = header.getName();
				String value = header.getValue();
				if ("Set-Cookie".equalsIgnoreCase(name)) {
					cookieStore = new BasicCookieStore();
					String[] strs = value.split(";");
					for (String str : strs) {
						String[] cookies = str.split("=");
						//System.out.println("=============== : " + cookies[0] + ":"	+ cookies[1]);
						cookieStore.addCookie(new BasicClientCookie(cookies[0], cookies[1]));
					}
				}
				reqBuilder.addHeader(header);
			}
		}
	}
	
	/**
	 * Process rq args.
	 *
	 * @param params the params
	 */
	protected void processRqArgs(Object...params){
		/**
		 * 对传递参数的处理
		 */
		if(params!=null&&params[1]!=null){
			Object p1 = params[1];
			if(p1 instanceof Map){
				Map<String,String>kvs = (Map<String,String>)params[1];
				if(kvs!=null&&kvs.size()!=0){
					for (Map.Entry<String, String> kv : kvs.entrySet()) 
							reqBuilder.addParameter(kv.getKey(), kv.getValue());
				}
			}
			//json para process...
			if(p1 instanceof String){
				String para = p1.toString();
				if(JsonValidator.checkJsonValid(para)){
					HttpEntity he = new StringEntity(para,CHARSET_UTF8);
					reqBuilder.setEntity(he);
				}
			}
		}
	}
	
	/**
	 * Sets the cookie store.
	 */
	protected void setCookieStore(){
		httpClientBuilder.setDefaultCookieStore(cookieStore);
	}
	
	/**
	 * Gets the response content.
	 *
	 * @param response the response
	 * @return the response content
	 */
	protected String getResponseContent(HttpResponse response){
		
		Header[]  hs = response.getHeaders(HttpHeaders.CONTENT_TYPE);
		
		if(charset==null)
			charset = CHARSET_UTF8;
		
		if(hs!=null&&hs.length>0){
			String type  = hs[0].getValue();
			if(type.contains("charset")){
				String cs = MatchUtils.getOnlyMatchs(type, "charset=([a-zA-Z\\-0-9]+)",1);
				if(cs!=null && !cs.equals("")) charset = cs; 
			}/*else{
				charset = getCharsetByResp(inStream);
			}*/
		}
		System.out.println("相应头编码:"+charset);
		// 获取响应内容
		InputStream inStream = null;
		BufferedReader reader = null;
		HttpEntity responseEntity = response.getEntity();
		try {
			inStream = responseEntity.getContent();
			reader = new BufferedReader(new InputStreamReader(inStream, charset));
			StringBuffer result = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				result.append(line+"\n");
			}
			return result.toString();
		} catch (IllegalStateException e) {
			LOG.error("不合法的状态异常!",e);
		} catch (IOException e) {
			LOG.error("读写异常!",e);
		}finally{
			IOUtils.closeQuietly(inStream);
			IOUtils.closeQuietly(reader);
		}
		return null;
	}
	
	private String getCharsetByResp(InputStream inStream){
		String charset = CHARSET_UTF8;
		// 获取响应内容
		
		try {
			charset = MatchUtils.getOnlyMatchs(IOUtils.toString(inStream), "charset=([a-zA-Z\\-0-9]+)",1);
		} catch (Exception e) {
			LOG.error("读写异常!",e);
		}
		
		return charset;
	}

}
