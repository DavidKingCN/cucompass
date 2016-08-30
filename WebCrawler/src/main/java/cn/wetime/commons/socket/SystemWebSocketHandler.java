/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.commons.socket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SystemWebSocketHandler implements WebSocketHandler {

	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		System.out.println("SystemWebSocketHandler connect to the websocket success......");
		//session.sendMessage(new TextMessage("Server:connected OK!"));
		
	}

	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		/*TextMessage returnMessage = new TextMessage("SystemWebSocketHandler:"
				+ message.getPayload() + " received at server");*/
//		while(true){
			System.out.println("console:"+Global.globalConstant);
			TextMessage returnMessage = new TextMessage(Global.globalConstant+++"");
			session.sendMessage(returnMessage);
//		}

	}

	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		System.out
				.println("SystemWebSocketHandler connection closed......");
	}

	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		System.out
				.println("SystemWebSocketHandler connection closed......");
	}

	public boolean supportsPartialMessages() {
		return false;
	}

}