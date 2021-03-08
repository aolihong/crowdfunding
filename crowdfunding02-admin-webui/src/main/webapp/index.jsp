<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<!-- 使当前页面的相对路径都是基于base标签。路径是使用jsp的pageContext域对象 -->
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>

<!-- 引入jquery文件 -->
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		/* 为id为“btn1”的标签绑定单击事件 */
		$("#btn1").click(function(){
			/* 需要发送的数据，先进行json数据定义 */
			/* 这里需要注意json数据的定义语法(K-V)*/
			var student = {
				"stuId" : 1,
				"stuName" : "aolhon",
				"address" : {
					"province" : "贵州省",
					"city" : "盘州市",
					"street" : "16"
				},
				"subjectList" : [{
					"subjectName" : "JAVA",
					"subjectScore" : "80"
				},{
					"subjectName" : "SSM",
					"subjectScore" : "75"
				}],
				"map" : {
					"k1" : "v1",
					"k2" : "v2"
				}
			}
			
			/* 将json对象数据转换成json字符串 */
			var requestBody = JSON.stringify(student);
			
			/* 发送的Ajax请求 */
			$.ajax({
				"url" : "jsonObject.json",							//请求目标资源的地址
				"type" : "post",									//请求方式
				"data" : requestBody,								//要发送的数据（请求参数）
				"contentType" : "application/json;charset= UTF-8",	//设置请求体的内容类型：可以是请求的数据格式（设置为json）、字符集（设置为UTF-8）
				"dataType" : "json",								//服务器响应的数据格式（设置为json）
				"success" : function(responseName){					//服务器端处理请求成功后调用的回调函数。responseName是 响应体数据（为自定义的名称）
					console.log(responseName);
					alert(responseName);
				},
				"error" : function(responseName){					//服务器端处理请求失败后调用的回调函数。responseName是 响应体数据（为自定义的名称）
					console.log(responseName);
				}
			})
		});
			
	});

</script>
</head>
<body>

	<a href="test/ssm.html">测试ssm整合的环境</a>
	<br/>
	<br/>
	<button id="btn1">发送json数据对象</button>
</body>
</html>