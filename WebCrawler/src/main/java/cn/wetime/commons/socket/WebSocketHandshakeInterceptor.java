/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.commons.socket;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor { 
  
    private static Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class); 
    public boolean beforeHandshake(ServerHttpRequest request, 
    		ServerHttpResponse response, 
    		WebSocketHandler wsHandler, 
    		Map<String, Object> attributes) throws Exception { 
    	System.out.println("WebSocketHandshakeInterceptor Before Handshake");
        return true; 
    } 
  
    public void afterHandshake(ServerHttpRequest request, 
    		ServerHttpResponse response, 
    		WebSocketHandler wsHandler, 
    		Exception exception) { 
    	System.out.println("WebSocketHandshakeInterceptor After Handshake");

    } 
}