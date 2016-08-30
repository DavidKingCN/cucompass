<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>首页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript"
	src="<%=basePath%>resources/jquery-1.9.1.min.js"></script>
<style type="text/css">
a {
	border: 1px solid rgb(73, 58, 58);
	background-color: rgb(133, 133, 133);
	height: 50px;
	line-height: 50px;
	color: white;
	text-decoration: none;
	font-weight: bold;
	padding: 5px;
	margin: 5px;
}
</style>
<script type="text/javascript">

function deleteUser(id){
	$.ajax({
		type: 'delete',
		url:'<%=basePath%>user/'+id,
		dataType:'text', 
		success:function(data){
			if(data=="suc"){
				alert("删除成功");
				location.reload();
			}
		},
		error:function(data){
		}
	});
}

</script>
</head>

<body>
	<div style="margin:0 auto;width:500px;">
		<a href="<%=basePath%>user/add">新增用户</a>
		<table>
			<tr>
				<th>用户ID</th>
				<th>用户名称</th>
				<th>操作</th>
			</tr>
		
			<c:forEach var="user" items="${list }">
				<tr>
					<td>${user.id }</td>
					<td>${user.userName }</td>
					<td>
						<a href="<%=basePath %>user/${user.id}/edit">编辑用户</a>
						<a href="<%=basePath %>user/${user.id}">查看用户</a>
						<a href="javascript:void(0);" onclick="deleteUser(${user.id })">删除该用户</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
