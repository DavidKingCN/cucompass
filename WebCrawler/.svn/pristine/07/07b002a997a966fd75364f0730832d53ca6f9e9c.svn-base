<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<!-- 	<meta http-equiv="refresh" content="5"> -->
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- 	 <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script> -->
<script src="http://cdn.bootcss.com/sockjs-client/0.3.4/sockjs.min.js"></script>
	 <script src="<%=basePath%>resources/jquery-1.9.1.min.js"></script>
        <script> 
            var websocket; 
            if ('WebSocket' in window) { 
                websocket = new WebSocket("ws://localhost:8080/p2pmart/webSocketServer"); 
                //alert("execute..option 1");
            } else if ('MozWebSocket' in window) { 
                websocket = new MozWebSocket("ws://localhost:8080/p2pmart/webSocketServer");
                //alert("execute..option 2"); 
            } else { 
                websocket = new SockJS("http://localhost:8080/p2pmart/sockjs/webSocketServer");
                //alert("execute..option 3"); 
            } 
            websocket.onopen = function (event) { 
            	//alert("on open");
            }; 
            websocket.onmessage = function (event) {
            	
                $("#msgcount").html("<font color='red'>"+event.data+"</font>");
            }; 
            websocket.onerror = function (event) { 
            }; 
            websocket.onclose = function (event) {
            	//alert("on close.."); 
            }; 
  
        </script>
        <style type="text/css">
        .msgClass{
        	border:1px solid blue;
     		width:300px;
     		height:300px;
        }
        
        </style>
  </head>
  
  <body>
    This is my JSP page. <br>
    <div id="msgcount"  class="msgClass"></div>
  </body>
</html>
