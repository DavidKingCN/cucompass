//弹出框的处理
$('.mask_close,.cancel').click(function(){
    $('.mask').fadeOut();
});
$('.active').click(function(){
    $('.mask').show();
});




/***查询页面demo_query_users.html的js-开始***/

function load_list(){
	var userName_ = $('[name=userName]').val();
	userName_ = userName_.trim();
	if(userName_!=null&&userName_!=''&&userName_!=undefined){
		pagenow =1;
		pagesize = 10;
	}
	$.ajax({
		url:'/crud/user/queryEntry',
		type:'GET',
		dataType:'json',
		data:{pageNo:pagenow,pageSize:pagesize,userName:userName_},
		success:function(respObj){
			console.log(JSON.stringify(respObj));
			if(pageflag){
			//生成分页栏
				pageall = Math.ceil(respObj.data.total/pagesize);
				loadpages();
			}
			var list = respObj.data.result;
			$("#userTab").empty();
			var dataHeader ="<tr><th>用户名</th><th>密码</th><th>性别</th><th>年龄</th><th>操作</th></tr>";
			$("#userTab").append(dataHeader); 
				
		    for(var i=0; i<list.length; i++){
				var record = list[i];
				var dataItem = "";
				dataItem += "<tr>";
				dataItem += "<td>"+isnull(record.userName)+"</td>";
				dataItem += "<td>"+isnull(record.userPwd)+"</td>";
				dataItem += "<td>"+isnull(record.userSex)+"</td>";
				dataItem += "<td>"+isnull(record.userAge)+"</td>";
				dataItem += "<td><a href='#' onclick='toModify("+record.userId+")'>修改</a>&nbsp;&nbsp;&nbsp;<a href='#' onclick='toDetail("+record.userId+")'>详情</a></td>";
				dataItem += "</tr>";
				$("#userTab").append(dataItem); 
		    }
 		},
		error: function(){
		}
		});
}
function isnull(a){
	if(a==null||a==undefined ||a==""){
		return  "";
	}else{
		return  a;
	}
}

//以下两个参数为初始化参数，可根据具体要求修改
//当前页码 
var pagenow = 1;
//单页显示条数
var pagesize = 5;
//单行显示列数
var colsize = 3;
//总页数
var pageall = 1;
//分页开关
var pageflag = true;
//获取总页码，添加分页
function loadpages(){
	//加载分页
	$(".tcdPageCode").createPage({
		pageCount:pageall,//总页数
		current:pagenow,//初始化页码为1， 代表第一页
		backFn:function(p){//p当前页
			pagenow = p;
			load_list();
		}
	});
}



function toAdd1(){
	//传递参数
	if($.cacheEnabled()){
		
	}else{
		
	}
	
	window.location.href = 'demo_user_and_group.html';
}
function toAdd2(){
	//传递参数
	if($.cacheEnabled()){
		
	}else{
		
	}
	window.location.href = 'demo2_user_and_group.html';
}
/***
 * 跳转到修改页面前的js处理代码 
 */
function toModify(id){
	if($.cacheEnabled()){
		$.setCache('userId',id);
		//传递json串
		$.setCache('userInfo',JSON.stringify({userId:id,userName:"戴凯"}));
		//跳转到新页面
		window.location.href = 'demo_modify_user.html';
	}else{
		//组装对象
		var argObj = {userId:id,userName:"戴凯"};
		//对象转json字符串
		var argStr = JSON.stringify(argObj);
		//对字符串进行编码
		argStr = $.encode(argStr);
		console.log('argStr='+argStr);
		//页面跳转 只是页面间跳转的一种情况
		//另外的页面跳转还有如下     如果遇到则传递方式根据实际情况再做处理
		//*****情形2： window.open(); 
		//*****情形3：页面包含关系的跳转  
		//*****情形4：父子窗口弹框 
		//*****情形5：frame框架页面跳转的处理
		//注：术语可能存在错误，仅供参考
		window.location.href = 'demo_modify_user.html?userId='+id+"&userInfo="+argStr;
	}
}
function toDetail(id){
	if($.cacheEnabled()){
		$.setCache('userId',id);
		window.location.href = 'demo_detail_user.html';
	}else{
		window.location.href = 'demo_detail_user.html?userId='+id;
	}
}
/***查询页面的js-结束***/



/***添加页面demo_user_and_group.html，demo2_user_and_group.html的js-开始***/

function submitForm1(){
	$('.submit').click(function(){
		//后台参数对象
		var args = {};
		//实例化组对象信息
		var group = {};
		//页面取值：组-组名
		group.groupName = $("[name=groupName]").val();
		//组装后台参数对象-赋值组对象
		args.group = group;
		//实例化用户对象信息
		var user = {};
		//页面取值：用户-用户名
		user.userName = $("[name=userName]").val();
		//页面取值：用户-密码
		user.userPwd = $("[name=userPwd]").val();
		//页面取值：用户-性别 
		user.userSex = $("[name=userSex]").val();
		//页面取值：用户-年龄 
		user.userAge = $("[name=userAge]").val();
		//组装后台参数对象-赋值用户对象
		args.user = user;
		//后台参数转换成json串
		var jsonArgs = JSON.stringify(args);
		//打印验证json串，查看是否符合后台需求，出差可以将该json串从控制台打印copy-paste to 后台开发人员 
		console.log('args = '+ args);
		if($('#userForm').formCheck())
			$.ajax({
				url:'/crud/user/addUserByGroup',
				type:'POST',
				contentType:'application/json;charset=utf-8',
				data:jsonArgs,
	    		async:true,
				dataType:'json',
				success:function(result){
					//延时刷新页面
					//setTimeout("window.location.reload()", 1000);
					window.location.href = 'demo_query_users.html';
				}
		 	});
		$('.mask').fadeOut();
	});
}
function submitForm2(){
	
		$('.submit').click(function(){
			//校验form表单数据是否符合输入规则
			if($('#userForm').formCheck())
				$.ajax({
					url:'/crud/user/addUserByGroup',
					type:'POST',
					contentType:'application/json;charset=utf-8',
					data:$("#userForm").formToJson(),
		    		async:true,
					dataType:'json',
					success:function(respObj){
						//延时刷新页面
						//setTimeout("window.location.reload()", 5000);
						window.location.href = 'demo_query_users.html';
					}
			 	});
			$('.mask').fadeOut();
		});
}
/***添加页面的js-结束***/



/***修改页面demo_modify_user.html的js-开始***/
function loadModifyPage(){
	//修改页面对参数的处理部分
	var userId;
	var userInfo;
	if($.cacheEnabled()){
		//获取简单数据
		userId = $.getCache('userId');
		//获取json数据并将json数据实例化为对象
		userInfo = JSON.parse($.getCache('userInfo'));
		$.delCache('userId');
		$.delCache('userInfo');
	}else{
		userId = UrlArgs.getArg('userId');
		/**开始》》处理json串转换为对象的操作**/
		var argStr = UrlArgs.getArg('userInfo');
		argStr = $.decode(argStr);
		userInfo = JSON.parse(argStr);
		/**结束》》处理json串转换为对象的操作**/
	}
	
	$.ajax({
		url:'/crud/user/queryUserAndGroup',
		type:'GET',
		data:{userId:userId},
		async:true,
		dataType:'json',
		success:function(respObj){
			//打印回调的相应正文
			var user = respObj.data.result.user;
			var group = respObj.data.result.group;
			
			$.jsonToDom(user);
			$.jsonToDom(group);
			
			//其他特殊的dom节点的处理 在这里补充--开始
			
			//其他特殊的dom节点的处理 在这里补充--结束
		}
		});
}


function submitModifyForm(){
	$('.submit').click(function(){
		
		/**通过form表单提取数据**/
		if($('#userForm').formCheck())
			$.ajax({
				url:'/crud/user/modifyUserByGroup',
				type:'POST',
				contentType:'application/json;charset=utf-8',
				data:$("#userForm").formToJson(),
				async:true,
				dataType:'json',
				success:function(respObj){
					window.location.href = 'demo_query_users.html';
				}
		 	});
		$('.mask').fadeOut();
	});
	
}
/***修改页面的js-结束***/



/***详情页面demo_detail_user.html的js-开始***/
function loadDetailPage(){
	//修改页面对参数的处理部分
	var userId;
	var userInfo;
	if($.cacheEnabled()){
		//获取简单数据
		userId = $.getCache('userId');
		//将不需要的缓存删除 
		$.delCache('userId');
	}else{
		userId = UrlArgs.getArg('userId');
	}
	
	$.ajax({
		url:'/crud/user/queryUserAndGroup',
		type:'GET',
		data:{userId:userId},
		async:true,
		dataType:'json',
		success:function(respObj){
			var user = respObj.data.result.user;
			var group = respObj.data.result.group;
			
			$.jsonToDom(user);
			$.jsonToDom(group);
			
			//其他特殊的dom节点的处理 在这里补充-开始
			
			
			//其他特殊的dom节点的处理 在这里补充-结束
		}
		});
}
function goBack(){
	$('.active').click(function(){
		window.location.href = 'demo_query_users.html';
	});
}
/***详情页面的js-结束***/



/***上传文件页面multiFileUpload.html的js-开始***/
function addNewItem(){
	$("#newAddOneBtn").click(function(){
		var insertHtml = "<tr><td>";
		insertHtml += "文件名称：<input type=\"text\" name=\"title"+(fileNo++)+"\" value=''/>";
		insertHtml += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		insertHtml += "请上传文件：<input type=\"file\" name=\"file"+(fileNo++)+"\" value='文件上传'/>";
		insertHtml += "</td></tr>";
		$('#fileTab').find('tr').eq(0).after(insertHtml);
	});
}
function uploadFiles(){
	$('.submit').click(function(){
		$(":file").each(function(i,item){
		    var v = $(item).val();
		    if(v ==null ||v ==''){
		    	window.location.reload();
		    	return;
		    }
		  });
		var args = {};
		
		var argsArr = [];
		var s = {};
		s.name = 'daikai';
		s.age = 12;
		s.sex = 'male';
		argsArr.push(s);
		
		s = {};
		s.name = 'daikai2';
		s.age = 14;
		s.sex = 'female';
		argsArr.push(s);
		
		args.students = argsArr;
		
		var argStr = JSON.stringify(args);
		$("#fileUploadForm").ajaxSubmit({
			url: '/crud/file/upload2?args='+encodeURI(argStr),
	        type: "post", 
	        dataType: "json",
	        success: function (data) {
	        	window.location.href = 'demo_query_users.html';
	        },
	        error: function (error) { alert(error); }
	    });
		$('.mask').fadeOut();
	});
}
/***上传文件页面的js-结束***/


/***添加组页面demo_group.html的js-开始***/

function addGroup(){
	$('.submit').click(function(){
		$.ajax({
			url:'/crud/group/add',
			type:'POST',
			contentType:'application/json;charset=utf-8',
			data:$("#groupForm").formToJson(),
    		async:true,
			dataType:'json',
			success:function(respObj){
				window.location.href = 'demo_query_users.html';
			}
	 	});
		$('.mask').fadeOut();
	});
}

/***添加组页面demo_group.html的js-结束***/