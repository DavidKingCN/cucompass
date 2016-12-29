package cn.com.davidking.http.core;



import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

public final class FluentReq {
	
	public static String get(String url){
		String flag = "-1";
		String result = "";
		 try {
			 result=Request.Get(url)//.viaProxy(new HttpHost(proxyIp,proxyPort))
			 .connectTimeout(1000)
			 .socketTimeout(1000)
			 .execute().returnContent().asString();
		} catch (Exception e) {
			result = flag;
		}
		return result;
	}
	
	public static String get(String url,String proxyIp,int proxyPort){
		String flag = "-1";
		String result = "";
		 try {
			 result=Request.Get(url).viaProxy(new HttpHost(proxyIp,proxyPort))
			 .connectTimeout(1000)
			 .socketTimeout(1000)
			 .execute().returnContent().asString();
		} catch (Exception e) {
			result = flag;
		}
		return result;
	}
	
	public static String post(String url,String body,String proxyIp,int proxyPort){
		String flag = "-1";
		String result = "";
		try {
			result=Request.Post(url)
			.version(HttpVersion.HTTP_1_1)
			.bodyString(body, ContentType.APPLICATION_FORM_URLENCODED)
			.viaProxy(new HttpHost(proxyIp,proxyPort))
			.execute().returnContent().asString();
		} catch (Exception e) {
			result = flag;
		}
		return result;
	}
	
	public static String post(
			String url,
			Map<String,String> args
			){
		String flag = "-1";
		String result = "";
		
		Form f = Form.form();
		args.forEach((k,v)->{
			f.add(k, v);
		});
		
		try {
			result=Request.Post(url)
			        .bodyForm(f.build())
			        .execute().returnContent().asString();
		} catch (Exception e) {
			result = flag;
		}
		return result;
	}
	
	public static String post(
			String url,
			Map<String,String> args,
			String proxyIp,
			int proxyPort){
		String flag = "-1";
		String result = "";
		
		Form f = Form.form();
		args.forEach((k,v)->{
			f.add(k, v);
		});
		
		try {
			result=Request.Post(url)
			        //.addHeader("X-Custom-header", "stuff")
			        .viaProxy(new HttpHost(proxyIp, proxyPort))
			        .bodyForm(f.build())
			        .execute().returnContent().asString();
		} catch (Exception e) {
			result = flag;
		}
		return result;
	}
	
	public static String post(
			String url,
			Map<String,String> reqHeaders,
			Map<String,String> args,
			String proxyIp,
			int proxyPort){
		String flag = "-1";
		String result = "";
		
		//设置 url
		Request req= Request.Post(url).version(HttpVersion.HTTP_1_1);
		//设置请求头
		for(Entry<String,String> reqHeader:reqHeaders.entrySet()){
			String k = reqHeader.getKey();
			String v = reqHeader.getValue();
			req = req.addHeader(k, v);
		}
		
		//设置代理服务器
		req = req.viaProxy(new HttpHost(proxyIp, proxyPort));
		//设置form表单参数
		Form f = Form.form();
		args.forEach((k,v)->{ f.add(k, v); });
		try {
			result=req
			        .bodyForm(f.build())
			        .execute().returnContent().asString();
		} catch (Exception e) {
			result = flag;
		}
		return result;
	}
	
	
	public static boolean post(String url,Map<String,String> args,String proxyIp,int proxyPort,int repeat){
		boolean result = true;
		
		Form f = Form.form();
		args.forEach((k,v)->{
			f.add(k, v);
		});
		int i = 0;
		do{
			try {
				Request.Post(url)
				        //.addHeader("X-Custom-header", "stuff")
				        .viaProxy(new HttpHost(proxyIp, proxyPort))
				        .connectTimeout(1000)
						.socketTimeout(1000)
				        .bodyForm(f.build())
				        .execute().returnContent().asString();
			} catch (Exception e) {
				result = false;
				break;
			}
		}while(i++<repeat);
		
		return result;
	}
	/***
	 * repeat counter times if return 0 normal else useless
	 * @param url
	 * @param proxyIp
	 * @param proxyPort
	 * @param cursor
	 * @return
	 */
	public static boolean get(String url,String proxyIp,int proxyPort,int cursor){
		boolean enable = true;
		int i = 0;
		do{
			try {
				 String result= Request.Get(url).viaProxy(new HttpHost(proxyIp,proxyPort))
				 .connectTimeout(1000)
				 .socketTimeout(1000)
				 .execute().returnContent().asString();
				 if(result==null || result.equals("")){
					 enable = false;
					 break;
				 }
			} catch (Exception e) {
				enable = false;
				break;
			}
		}while(i<cursor);
		return enable;
	}
}
