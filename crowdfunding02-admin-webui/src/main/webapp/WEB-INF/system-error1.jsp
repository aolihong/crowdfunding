<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<!-- 头部导航 -->
<%@ include file="/WEB-INF/include-head.jsp"%>

<body>

	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<!-- 异常的信息 -->
				<!-- requestScope域对象的使用相当于request.getAttribute("exception").message -->
				<h3 style="text-align: center;">${requestScope.exception.message}</h3>
			</div>
		</div>
	</div>

</body>
</html>
