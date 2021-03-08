<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jstl标签库 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html lang="zh-CN">
<!-- 头部导航 -->
<%@ include file="/WEB-INF/include-head.jsp"%>

<!-- 加入pagination插件环境-->
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript">
	
	$(function(){
		// 调用后面声明的函数对页码导航条进行初始化操作
		initPagination();
		
		
		/* 删除商品的提醒 */
		$(".btn-danger").click(function(){
			// 【注意】
			// 当使用springsecurity框架插件使，从登陆账号获得数据，不是使用使用之前的相关域对象
			// 否则会出现一些问题
			// 而是使用springsecurity自身封装的属性从springsecurity标签库获取数据
			
			// 情景1：获取已登陆账号Id
//  			var loginAdminId = ${sessionScope.loginAdmin.id};

			// 情景2：获取已登陆账号Id
			var loginAdminId = <security:authentication property="principal.originalAdmin.id"/>
			
 			var deleteAdminId = $(this).parent().find("input").val();
// 			/* var deleteAdminId = 325; */
			if (loginAdminId == deleteAdminId || loginAdminId === deleteAdminId){
				layer.msg("不能删除已登陆账号");
				 return false; 
			} 
			return confirm("确认删除【" + $(this).parent().parent().find("td:eq(3)").text() + "】？");
		});
		

	
	});
	
	// 生成页码导航条的函数
	function initPagination() {
		
		// 获取总记录数
		var totalRecord = ${requestScope.pageInfo.total};
		
		// 声明一个JSON对象存储Pagination要设置的属性
		var properties = {
			num_edge_entries: 3,								// 边缘页数
			num_display_entries: 5,								// 主体页数
			callback: pageSelectCallback,						// 指定用户点击“翻页”的按钮时跳转页面的回调函数
			items_per_page: ${requestScope.pageInfo.pageSize},	// 每页要显示的数据的数量
			current_page: ${requestScope.pageInfo.pageNum - 1},	// Pagination内部使用pageIndex来管理页码，pageIndex从0开始，pageNum从1开始，所以要减一
			prev_text: "上一页",									// 上一页按钮上显示的文本
			next_text: "下一页"									// 下一页按钮上显示的文本
		};
		
		// 生成页码导航条
		$("#Pagination").pagination(totalRecord, properties);
		
	}
	
	// 回调函数的含义：声明出来以后不是自己调用，而是交给系统或框架调用
	// 用户点击“上一页、下一页、1、2、3……”这样的页码时调用这个函数实现页面跳转
	// pageIndex是Pagination传给我们的那个“从0开始”的页码
	function pageSelectCallback(pageIndex, jQuery) {
		
		// 根据pageIndex计算得到pageNum
		var pageNum = pageIndex + 1;
		
		// 跳转页面
		window.location.href = "admin/get/page.html?pageNum="+pageNum+"&keyword=${param.keyword}";
		
		// 由于每一个页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
		return false;
	}
	
</script>
<body>

	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form action="admin/get/page.html" method="post"
							class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input class="form-control has-success" type="text"
										placeholder="请输入查询条件" name="keyword"
										value="${empty param.keyword ? '' : param.keyword}">
								</div>
							</div>
							<button type="submit" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<!-- <button type="button" class="btn btn-primary"
							style="float: right;" onclick="window.location.href='add.html'">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button> -->

						<!-- 直接使用a标签进行请求 -->
						<a class="btn btn-primary" style="float: right;"
							href="admin/to/add/page.html"> <i
							class="glyphicon glyphicon-plus"></i> 新增
						</a> <br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox"></th>
										<th>账号</th>
										<th>名称</th>
										<th>邮箱地址</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>
									<!-- jstl标签 -->
									<c:if test="${empty requestScope.pageInfo.list}">
										<tr>
											<td colspan="5">抱歉！没有查询到相关的数据！</td>
										</tr>
									</c:if>
									<c:if test="${!empty requestScope.pageInfo.list }">
										<c:forEach items="${requestScope.pageInfo.list }" var="admin"
											varStatus="myStatus">
											<tr>
												<td>${myStatus.count }</td>
												<td><input type="checkbox"></td>
												<td>${admin.loginAcct }</td>
												<td>${admin.userName }</td>
												<td>${admin.email }</td>
												<td>
												<a href="assign/to/assign/role/page.html?adminId=${admin.id }
														&pageNum=${requestScope.pageInfo.pageNum }
														&keyword=${param.keyword }"
													class="btn btn-success btn-xs"> 
													<i class=" glyphicon glyphicon-check"></i>
												</a> 
												<!-- <button type="button" class="btn btn-primary btn-xs">
														<i class=" glyphicon glyphicon-pencil"></i>
													</button> --> 
													<a class="btn btn-primary btn-xs"
													href="admin/to/edit/page.html?adminId=${admin.id }"> <i
														class=" glyphicon glyphicon-pencil"></i>
												</a> <!-- <button type="button" class="btn btn-danger btn-xs">
														<i class=" glyphicon glyphicon-remove"></i>
													</button> --> <!-- 这里使用<a> 标签取代 <button>是因为a标签请求跳转到后端使用href即可，
													而button标签还需要写一个js代码，绑定单击事件，很麻烦 --> <!-- 向控制器传递参数方法 --> <!-- （1）在请求路径后使用？添加传递参数，多个参数之间使用&分隔开 -->
													<!-- （2）springMVC框架中支持使用在关键请求路径后使用/添加传递参数，多个参数之间使用/分隔 --> <a
													class="btn btn-danger btn-xs"
													href="admin/remove/${admin.id }/${requestScope.pageInfo.pageNum}/${param.keyword }.html">
														<!-- i标签使用的是bootstrap的样式 --> <i
														class=" glyphicon glyphicon-remove"></i>
												</a>
												
												 <!-- 隐藏标签，供js代码中使用当前标签中的值 --> 
												<input type="hidden" value="${admin.id}" /></td>
											</tr>

										</c:forEach>
									</c:if>


								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<div id="Pagination" class="pagination">
												<!-- 这里显示分页 -->
											</div>
										</td>
									</tr>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>
