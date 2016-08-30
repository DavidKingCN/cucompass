/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.commons.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer { 
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) { 
        registry.addHandler(systemWebSocketHandler(),"/webSocketServer")
        		.addInterceptors(new WebSocketHandshakeInterceptor());
        
        System.out.println("msgPush WebSocketConfig registed!");
        
        registry.addHandler(systemWebSocketHandler(), "/sockjs/webSocketServer")
        		.addInterceptors(new WebSocketHandshakeInterceptor()) 
                .withSockJS(); 
    } 
  
    @Bean
    public WebSocketHandler systemWebSocketHandler(){ 
        return new SystemWebSocketHandler(); 
    } 
  
}