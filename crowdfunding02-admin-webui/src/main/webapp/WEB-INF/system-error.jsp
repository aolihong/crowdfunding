<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keys" content="">
<meta name="author" content="">
<base
	href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/" />
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/login.css">
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="layer/layer.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script>
	$(function(){
		$("button").click(function(){
			window.history.back();
		});
	});
</script>
<style>
</style>
<title>aolhon网</title>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<div>
					<a class="navbar-brand" href="index.html" style="font-size: 32px;">aolhon网-创意产品众筹平台</a>
				</div>
			</div>
		</div>
	</nav>

	<div class="container">

		<form action="admin/do/login/page.html" method="post" class="form-signin" role="form">
			<h2 class="form-signin-heading">
				<i class="glyphicon glyphicon-log-in"></i> aolhon网系统消息 
			</h2>
			
			<!-- 异常的信息 -->
			<!-- requestScope域对象的使用相当于request.getAttribute("exception").message -->
					<h3 style="text-align: center;">${requestScope.exception.message}</h3>
			<button style="width: 150px;margin: 50px auto 0px auto;" class="btn btn-lg btn-success btn-block">返回</button>
		</form>
	</div>
</body>
</html>