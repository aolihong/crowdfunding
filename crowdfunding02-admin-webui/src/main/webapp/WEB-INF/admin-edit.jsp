<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<!-- 头部导航 -->
<%@ include file="/WEB-INF/include-head.jsp"%>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript">
	$(function(){
 		
		updateMessage();

	});
	
	function updateMessage(){
		var updateMsg = $("#updateMsg").val(); 
		if (updateMsg == "msg" ){
			layer.msg("修改成功");
		}
		
	}
	
</script>
<body>
<input type="hidden" id="updateMsg" value="${param.msg}"/>
	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
					<li><a href="#">数据列表</a></li>
					<li class="active">修改</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-heading">
						表单数据
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<form action="admin/edit.html" role="form">
							<p style="color: red">${requestScope.exception.message }</p>
							
							<!-- 隐藏的标签 -->
							<input type="hidden" name="id" value="${requestScope.admin.id }"/> 
							<div class="form-group">
								<label for="exampleInputPassword1">登陆账号</label> 
								<input type="text" class="form-control" id="exampleInputPassword1"
									   placeholder="请输入更改登陆账号" name="loginAcct"
									   value="${requestScope.admin.loginAcct }"/>
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">登陆密码</label> 
								<input type="text" class="form-control" id="exampleInputPassword1"
									   placeholder="请输入更改登陆密码" name="userPswd"/>
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">用户昵称</label> 
								<input type="text" class="form-control" id="exampleInputPassword1"
									placeholder="请输入更改用户昵称" name="userName"
									value="${requestScope.admin.userName }"/>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">邮箱地址</label> 
								<input type="email" class="form-control" id="exampleInputEmail1"
									placeholder="请输入更改邮箱地址" name="email"
									value="${requestScope.admin.email }"/>
								<p class="help-block label label-warning">请输入合法的邮箱地址, 格式为：
									xxxx@xxxx.com</p>
							</div>
							<button type="submit" class="btn btn-success">
								<i class="glyphicon glyphicon-edit"></i> 修改
							</button>
							<button type="reset" class="btn btn-danger">
								<i class="glyphicon glyphicon-refresh"></i> 重置
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
