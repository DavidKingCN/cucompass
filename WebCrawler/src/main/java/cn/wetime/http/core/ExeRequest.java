/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.http.core;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.Map;

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
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

// TODO: Auto-generated Javadoc
/**
 * 请求执行类.
 *
 * @author daikai
 * @version 1.0
 */
@SuppressWarnings("all")
public class ExeRequest implements WtRequest {
	
	/** The Constant logger. */
	private static final Log logger = LogFactory.getLog(ExeRequest.class);
	
	/** The url. */
	private String url;
	
	/** The method name. */
	private String methodName;
	
	
	/**
	 * The Constructor.
	 *
	 * @param url the url
	 * @param methodName the method name
	 */
	public ExeRequest(String url, String methodName) {
		super();
		this.url = url;
		this.methodName = methodName;
	}

	/** The Constant CHARSET. */
	private static final String CHARSET = Consts.UTF_8.displayName();

	/** The Constant READ_BUFFER. */
	private static final int READ_BUFFER = 1024;
	
	/* (non-Javadoc)
	 * @see cn.wetime.http.core.WtRequest#exeRequest(java.lang.Object[])
	 */
	public synchronized String exeRequest(Object...params){
		
		CookieStore cookieStore = new BasicCookieStore();
		
		HttpClient httpclient= HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();
		
		
		HttpResponse response = null;
		RequestBuilder requestBuilder =null;
		try {
			requestBuilder = RequestBuilder
					.create(methodName)
					.setUri(new URI(url));
			/**
			 * 处理请求头
			 */
			
			if(params!=null&&params[0]!=null){
				List<Header> headers = (List<Header>)params[0];
				
				for(Header header:headers){
					
					String name = header.getName();
					String value = header.getValue();
					if ("Set-Cookie".equalsIgnoreCase(name)) {
						String[] strs = value.split(";");
						for (String str : strs) {
							String[] cookies = str.split("=");
							System.out.println("=============== : " + cookies[0] + ":"
									+ cookies[1]);
							cookieStore.addCookie(new BasicClientCookie(cookies[0], cookies[1]));
						}
					}
					
					requestBuilder.addHeader(header);
					
				}
			}
			/**
			 * 对传递参数的处理
			 */
			if(params!=null&&params[1]!=null){
				Map<String,String>kvs = (Map<String,String>)params[1];
				if(kvs!=null&&kvs.size()!=0){
					for (Map.Entry<String, String> kv : kvs.entrySet()) 
							requestBuilder.addParameter(kv.getKey(), kv.getValue());
				}
			}
			
				
			
			HttpUriRequest request = requestBuilder.build();
			
			
			response = httpclient.execute(request);
			/**
			 * 处理响应头
			 */
			if(params!=null&&params[2]!=null){
				List<Header> respHeaders = (List<Header>)params[2];
				for(Header header:respHeaders){
					response.addHeader(header);
				}
			}
			
			int rtType = ResponseTextType.CONTENT_TYPE_JSON; //默认返回类型非html json
			if(params!=null&&params[3] != null){
				rtType = (Integer)params[3];
			}
			
			Header[]  contentType = response.getHeaders(HttpHeaders.CONTENT_TYPE);
			/**
			 * 查看相应状态码是否是200
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
			
			/**
			 * 查看响应头的content-type 是否是 application/json 
			 * 是则继续
			 * 否则返回失败MediaType.TEXT_HTML_VALUE "text/html"
			 */
			if(contentType!=null && contentType.length!=0){
				Header typeHeader = contentType[0];
				if(rtType==ResponseTextType.CONTENT_TYPE_HTML){
					if( typeHeader.getValue().contains(MediaType.TEXT_HTML_VALUE))
						return new String(content);
					else
						return String.valueOf(NOT_MATCH);
				}
				
			}
			if(contentType!=null && contentType.length!=0){
				Header typeHeader = contentType[0];
				if(!typeHeader.getValue().contains(MediaType.APPLICATION_JSON_VALUE)){
					return String.valueOf(NOT_MATCH);
				}
			}else{
				return String.valueOf(FAIL);
			}
			
			if(content!=null&&!content.isEmpty())
				return content;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
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
	 * @param response API端向服务器端发起的响应
	 * @return the int
	 * @throws Exception the exception
	 */
	private int validateHttpCode(HttpResponse response) throws Exception{
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
	 * @return the response content
	 */
	public String getResponseContent(HttpResponse response){
		// 获取响应内容
		InputStream inStream = null;
		BufferedReader reader = null;
		HttpEntity responseEntity = response.getEntity();
		try {
			inStream = responseEntity.getContent();
			reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
			StringBuffer result = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (IllegalStateException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}finally{
			IOUtils.closeQuietly(inStream);
			IOUtils.closeQuietly(reader);
		}
		return null;
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
				result.append(new String(buffer,0,len, CHARSET));
			}
			return baos.toByteArray();
		} catch (IllegalStateException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}finally{
			IOUtils.closeQuietly(inStream);
			IOUtils.closeQuietly(baos);
		}
		return new byte[]{};
	}
	
	
}
