<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<!-- 头部导航 -->
<%@ include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />

<!-- 加入zTree环境 -->
<link rel="stylesheet" href="ztree/zTreeStyle.css"/> 
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript" src="myjs/my-role.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>

<script type="text/javascript">
	$(function() {
		/* 以下的变量、函数在页面刷新好后就会自动加载 */

		// 1.分页所需的数据（全局变量）
		window.pageNum = 1;
		window.pageSizw = 6;
		window.keyword = "";

		// 2.调用执行分页函数
		generatePage();

		// 给id为"searchBtn"的标签绑定单击事件
		$("#searchBtn").click(function() {
			window.keyword = $("#keywordInput").val();
			generatePage();
		});

		// 给添加按钮绑定单击事件，用来显示 添加模态框
		$("#showAddModalBtn").click(function() {
			// 开启id属性为addModal的模态框
			$("#addModal").modal("show");
		});

		// ********************************新增角色*****************************
		// 给模态框中的保存按钮添加单击事件
		$("#saveRoleBtn").click(function() {
			// 1.取出输入框中的文本值
			var roleName = $.trim($("#addModal [name=roleName]").val());

			// 2.发送ajax请求
			$.ajax({
				"url" : "role/add.json",
				"type" : "post",
				"data" : {
					"Name" : roleName
				},
				"dataType" : "json",
				/* 请求成功，服务器响应后成功调用函数 ，参数response是服务器响应的responseJSON的数据*/
				"success" : function(response) {
					/* 判断响应数据中的ResultEntity对象的result属性 */
					var result = response.result;

					if (result == "FAILED") {
						layer.msg("操作失败！ " + response.message);
					}

					if (result == "SUCCESS") {
						layer.msg("操作成功");

						/* 并将页面显示到最后一页，即刚刚添加的角色名称页 */
						window.pageNum = "999999";

						// 重新加载分页数据
						generatePage();
					}

				},
				/* 请求失败的响应数据是整个json对象，而不是json对象中的responseJSON数据 */
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});

			/* 修改完后，就立马关闭模态框，并吧模态框中的数据给清空 */

			/* 关闭模态框 */
			$("#addModal").modal("hide");

			/* 清空模态框中内容 */
			$("#addModal [name = roleName]").val("");
			
			

		});
		
		// ********************************修改角色*****************************
		
		/* 这里不能使用这样的单击事件，因为这个单击事件只针对于本页面当前的标签有效，一旦翻页后（ajax的响应生成）
		        则相同的标签就没有被绑定单击事件 ，所以不能这样使用*/
		
		/* $(".updateBtn").click(function(){
			alert("弹窗");
		}); */
		
		/* 为class属性为.updata。使用jQuery的on()函数 */
		$("#rolePageBody").on("click", ".updateBtn", function(){
			$("#editModal").modal("show");
			
			// 获取角色名、角色id
			// 局部变量
			var roleName = $(this).parent().prev().text();
			
			// 全局变量
			window.roleId = this.id;
			
			/* 【注意】 $(this) 与 this 区别
				$()是jQuery的函数
				this 是js的一个语法关键字，表示本标签
			*/
			
			// 设置模态框的输入框初始值
			$("#editModal [name = roleName]").val(roleName);
			
			
		});
		
		/* 为update模态框的按钮绑定单击事件进行ajax请求 */
		$("#updateRoleBtn").click(function(){
			// 角色名
			var roleName = $("#editModal [name = roleName]").val();
			
			// ajax请求是调用ajax()函数，然后数据是在()里面，且数据是以键值对形式存在
			$.ajax({
				"url" : "role/update.json",
				"type" : "post",
				"data" : {
					"id" : window.roleId,
					"name" : roleName 
				},
				"dataType" : "json",
				"success" : function(response){
					// ResultEntity对象result属性值
					var result = response.result;
					if(result == "FAILED"){
						layer.msg("操作失败！" + response.message);
					}
					
					if(result == "SUCCESS"){
						layer.msg("操作成功");
						generatePage();
					}
				},
				"error" : function(response){
					layer.msg("错误：" + response.staus + " " + response.statusText);
				}
			});
			
			// 关闭模态框
			$("#editModal").modal("hide");
			
			// 清空模态框中的输入框值
			$("#editModal [name = roleName]").val("");
			
		});
		
		// ********************************删除角色*****************************
		// --------------删除单个角色--------------
		
		// 给删除按钮绑定单击事件
		$("#rolePageBody").on("click", ".removeBtn", function(){

			// 得到角色名
			var roleName = $(this).parent().prev().text();
			// 封装数据
			var roleArray = [{
				"roleId" : this.id,
				"roleName" : roleName
			}];
			
			showConfirmModal(roleArray);
		});
		
		// --------------删除多个角色--------------
		
		// 给总的checkbox绑定单击响应函数
		$("#summaryBox").click(function(){
			
			// ①获取当前多选框自身的状态
			var currentStatus = this.checked;
			
			// ②用当前多选框的状态设置其他多选框
			$(".itemBox").prop("checked", currentStatus);
			
		});
		
		// 设置总复选框状态为false
		$("#summaryBox").prop("checked", false);
		
		// 全选、全不选的反向操作
		$("#rolePageBody").on("click",".itemBox",function(){
			
			// 获取当前已经选中的.itemBox的数量
			var checkedBoxCount = $(".itemBox:checked").length;
			
			// 获取全部.itemBox的数量
			var totalBoxCount = $(".itemBox").length;
			
			// 使用二者的比较结果设置总的checkbox
			$("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount);
			
		});
		
		// 给批量删除按钮绑定删除事件
		$("#batchRemoveBtn").click(function(){
			
			var roleArray = [];
			
			// 遍历已选上的复选框
			$(".itemBox:checked").each(function(){
				
				// 获得roleId、roleName
				var roleId = this.id;
				var roleName = $(this).parent().next().text();
				
				// 封装数据到数组
				roleArray.push({
					"roleId":roleId,
					"roleName":roleName
				});
				
				// 检查roleArray的长度是否为0
				if(roleArray.length == 0) {
					layer.msg("请至少选择一个执行删除");
					return ;
				}
				
				// 调用专门的函数打开模态框
				showConfirmModal(roleArray);
				
			});
		});
		
		// 给模态框确认删除按钮绑定单击事件
		$("#removeRoleBtn").click(function(){
			
			
			
			// 从全局变量范围获取roleIdArray，转换为JSON字符串
			var requestBody = JSON.stringify(window.roleIdArray);
			
			$.ajax({
				"url" : "role/remove/by/role/id/array.json",
				"type" : "post",
				"data" : requestBody,
				"contentType" : "application/json;charset=UTF-8",
				"dataType" : "json",
				"success" : function(response){
					var result = response.result;
					if (result == "FAILED"){
						layer.msg("操作失败！"+response.message);
					}
					
					if(result == "SUCCESS"){

						layer.msg("操作成功！");

						// 重新加载分页数据
						generatePage();
					}
				},
				"error" : function(response) {
					layer.msg(response.status+" "+response.statusText);
				}
			});
			
			// 关闭模态框
			$("#confirmModal").modal("hide");
			
			// 设置总复选框状态为false
			$("#summaryBox").prop("checked", false);
		});

		
		
		// ********************************修改角色权限*****************************
		
		// 给修改按钮绑定单击事件
		$("#rolePageBody").on("click", ".checkBtn", function(){
			window.roleId = this.id;
			
			$("#assignModal").modal("show");
			
			// 显示树形结构
			fillAuthTree();
		});
		
		// 给修改权限确认按钮绑定单击事件
		$("#assignAuthBtn").click(function(){
			
			// (1) 获取模态框中被选中的authId
			
			//声明一个专门的数组存放 id 
			var authIdArray = [];
			
			// 获取 zTreeObj 对象 
			var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
			
			// 得到被选中的节点
			var checkedNodes = zTreeObj.getCheckedNodes();
			
			// 遍历
			for(var i = 0; i < checkedNodes.length; i++){
				var checkedNode =  checkedNodes[i];
				var authId = checkedNode.id;
				authIdArray.push(authId);
			}
			
			
			
			// (2) 请求authId数据到服务器
			
			// ②发送请求执行分配 
			var requestBody = { 
				"authIdArray":authIdArray, 
				// 为了服务器端 handler 方法能够统一使用 List<Integer>方式接收数据，roleId 也存 入数组 
				
				"roleId":[window.roleId] 
			};
			
			requestBody = JSON.stringify(requestBody);
			
			$.ajax({
				"url" : "assign/do/role/assign/auth.json",
				"type" : "post",
				"data" : requestBody,
				"contentType" : "application/json;charset=UTF-8",
				"dataType" : "json",
				"success" : function(response){
					var result = response.result; 
					if(result == "SUCCESS") {
						layer.msg("操作成功！"); 
					}
					if(result == "FAILED") {
						layer.msg("操作失败！"+response.message); 
					}
				},
				"error" : function(response){
					layer.msg(response.status+" "+response.statusText);
				}
			});
			
			$("#assignModal").modal("hide");
		});
			
	});
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
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="keywordInput" class="form-control has-success"
										type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button type="button" class="btn btn-warning" id="searchBtn">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger"
							id="batchRemoveBtn"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary" id="showAddModalBtn"
							style="float: right;">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input id="summaryBox" type="checkbox"></th>
										<th>名称</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody id="rolePageBody">
									<!-- 这里使用json数据进行js动态填充 -->

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

	<!-- 一般放置末尾 -->
	<%@ include file="/WEB-INF/modal-role-add.jsp"%>
	<%@ include file="/WEB-INF/modal-role-edit.jsp"%>
	<%@ include file="/WEB-INF/modal-role-confirm.jsp"%>
	<%@ include file="/WEB-INF/modal-role-assign-auth.jsp" %>

</body>
</html>
