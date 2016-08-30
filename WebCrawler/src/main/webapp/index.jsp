<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>


<script language="javascript" type="text/javascript">
	//获取本地IP 
	function GetLocalIPAddress() {
		var obj = null;
		var rslt = "";
		try {
			obj = new ActiveXObject("rcbdyctl.Setting");
			rslt = obj.GetIPAddress;
			obj = null;
		} catch (e) {
			alert("ErrInfoIS:" + e)
		}
		return rslt;
	}
	document.write("你的IP是：" + GetLocalIPAddress());
</script>
<head>
<!-- 	<meta http-equiv="refresh" content="0;url=<%=basePath%>user"> -->
</head>
<body>
	<h2>登陆页面</h2>
	<form action="<%=basePath%>user/login" method="post">
		用户名：<input name="username" type="text" value=""><br>
		密码&nbsp; ：<input name="password" type="password" value=""><br>
		<input type="submit" value="马上登陆">
	</form>
</body>
</html>
