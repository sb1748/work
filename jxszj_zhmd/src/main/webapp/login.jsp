<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href=" <%=basePath%>">
<title>物料管理系统</title>
<link rel="stylesheet" type="text/css" href="css/style.css">

<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/vector.js"></script>
</head>
<body>
<div id="container">
	<div id="output">
		<div class="containerT">
			<h1>物料管理系统</h1>
			<form class="form" id="entry_form" action="${pageContext.request.contextPath}/sign" method="post">
				<input type="text" placeholder="用户名" name="name" id="entry_name" >
				<input type="password" placeholder="密码" name="pwd" id="entry_password">
				<button type="submit" id="entry_btn">登录</button>
				<div id="prompt" class="prompt">${msg }</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
    $(function(){
        Victor("container", "output");   //登录背景函数
        $("#entry_name").focus();
        $(document).keydown(function(event){
            if(event.keyCode==13){
                $("#entry_btn").click();
            }
        });
    });
    
//     $("#entry_btn").click(function(){
//     	var name=$("#entry_name").val();
//     	var pwd=$("#entry_password").val();
//     	$.ajax({  
// 		     type: 'GET',   
// 		     dataType : 'json', 
// 		     data: {name:name,pwd:pwd},
// 		     url: "${pageContext.request.contextPath}"+"/userInfo/login",   
// 		     success: function(data){   
// 		     }  
// 		  }); 
//     	});
    
</script>
</body>
</html>