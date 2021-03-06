/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
@SuppressWarnings("all")
public class HttpUtils {
	//通过URL方式解析页面
	
	//通过httpclient解析数据
	
	public static String printPageInfo(Map<String,String> headers,String url) throws Exception {
		System.out.println("url path:"+url);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		HttpEntity entity;
		List<Cookie> cookies;
		HttpPost httppost1 = new HttpPost(url);
		//设置请求头
		if(headers!=null)
			for(Map.Entry<String, String> item:headers.entrySet()){
				httppost1.addHeader(item.getKey(), item.getValue());
			}
		
		response = httpclient.execute(httppost1);
		response.addHeader("Content-Type", "application/json;charset=UTF-8");
		response.addHeader("Content-Language", "zh-CN");
		
		
		entity = response.getEntity();
		System.out.println("状态码："+response.getStatusLine());
		if (response.getStatusLine().toString().indexOf("HTTP/1.1 200 OK") >= 0) {
		}
		if (entity != null) {
			System.out.println("响应正文长度: " + entity.getContentLength());
		}
		//读取响应正文
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
		StringBuffer result = new StringBuffer("");
		String line = null;
		while ((line = reader.readLine()) != null) {
			//System.out.println(line);
//			result.append(new String(line.getBytes(),"UTF-8"));
			result.append(line);
		}
		return result.toString();
	}
	/**
	 * url 网络地址
	 * file windows:C:\path\file.suffix
	 */
	public static void getInfoFromURL(String url,String path,String fileName){
		BufferedReader br  = null;
		FileWriter fileWriter = null;
		try {
			//creaet url 
			URL urlInfo = new URL(url);
			//open connect
			URLConnection connection = urlInfo.openConnection();
			//set connection properties
			connection.addRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.addRequestProperty("accept-language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//检测文件存在否 ，不存在则创建 ，存在则删除重建
			if(!FileUtil.existDirectory(path)){
				FileUtil.createDirectory(path);
			}
			if(FileUtil.existFile(path, fileName)){
				FileUtil.deleteFile(path, fileName);
			}
			fileWriter = new FileWriter(path + File.separator+fileName,true);
			
			String line = "";
			while((line=br.readLine())!=null){
				fileWriter.write(line);
		        fileWriter.flush();
			}
			
		} catch (Exception e) {
			// TODO some exceptions 
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fileWriter.close();
			} catch (IOException e) {
				// TODO 
				e.printStackTrace();
			}
		}
	}
	
}
