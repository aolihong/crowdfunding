<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<!-- 头部导航 -->
<%@ include file="/WEB-INF/include-head.jsp"%>
<script type="text/javascript" src="layer/layer.js"></script>

<!-- 引入ztree插件的css文件、js文件 -->
<!-- ztree是依靠jQuery实现的树插件 -->
<link rel="stylesheet" href="ztree/zTreeStyle.css" />
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript" src="myjs/my-menu.js"></script>
<script type="text/javascript">
	$(function(){
		// 调用自定义好的函数进行初始化树形结构
		generateTree();
		
		// ********************************添加菜单*****************************
		// 给添加按钮绑定单击事件
		$("#treeDemo").on("click", ".addBtn", function(){
			// 将当前节点的id，作为新节点的pid保存到全局变量
			window.pid = this.id;
			
			// 打开模态框
			$("#menuAddModal").modal("show");
			
			return false;
		});
		
		// 给模态框保存按钮绑定单击事件
		$("#menuSaveBtn").click(function(){
			// 获得节点名称
			var name = $.trim($("#menuAddModal [name = name]").val());
			// 获得URL地址
			var url = $.trim($("#menuAddModal [name = url]").val());
			// 获得icon图标
			var icon = $.trim($("#menuAddModal [name = icon]:checked").val());
			
			// 发送ajax请求
			$.ajax({
				"url" : "menu/add.json",
				"type" : "post",
				"data" : {
					"pid" : window.pid,
					"name" : name,
					"url" : url,
					"icon" : icon
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
						
						// 重新加载树形结构
						generateTree();
					}
				},
				"error" : function(response){
					layer.msg("错误：" + response.staus + " " + response.statusText);
				}
			});
			
			// 关闭模态框
			$("#menuAddModal").modal("hide");
			
			// 情况模态框数据
			// jQuery对象调用click()函数，里面不传任何参数，相当于用户点击了一下
			$("#menuResetBtn").click();
		});
		
		
		// ********************************修改菜单*****************************
		// 给修改按钮绑定单击事件
		$("#treeDemo").on("click", ".editBtn", function(){
			// 打开编辑模态框
			$("#menuEditModal").modal("show");
			
			// 获得zTreeObj对象
			var zTreeObj =  $.fn.zTree.getZTreeObj("treeDemo");
			
			// 获得节点id
			window.id = this.id;
			
			var key = "id"; 
			
			// 用来搜索节点的属性值 
			var value = window.id;
			
			var currentNode = zTreeObj.getNodeByParam(key, value);
			
			// 回显数据到表单
			$("#menuEditModal [name=name]").val(currentNode.name);		
			$("#menuEditModal [name=url]").val(currentNode.url);		
			$("#menuEditModal [name=icon]").val([currentNode.icon]);
			
			return false;
		});
		
		$("#menuEditBtn").click(function(){
			var name = $("#menuEditModal [name=name]").val();		
			var url = $("#menuEditModal [name=url]").val();		
			var icon = $("#menuEditModal [name=icon]").val();
			
			// ajax请求
			$.ajax({
				"url" : "menu/update.json",
				"type" : "post",
				"data" : {
					"id" : window.id,
					"name" : name,
					"url" : url,
					"icon" : icon
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
						
						// 重新加载树形结构
						generateTree();
					}
				},
				"error" : function(response){
					layer.msg("错误：" + response.staus + " " + response.statusText);
				}
			});
			$("#menuEditModal").modal("hide");
		});
		
		// ********************************删除菜单*****************************
		
		// 删除按钮绑定单击事件，打开模态框
		$("#treeDemo").on("click", ".removeBtn", function(){
			// 打开模态框
			$("#menuConfirmModal").modal("show");
			
			// 回显数据
			// 获得zTreeObj对象
			var zTreeObj =  $.fn.zTree.getZTreeObj("treeDemo");
			
			// 获得节点id
			window.id = this.id;
			
			var key = "id"; 
			
			// 用来搜索节点的属性值 
			var value = window.id;
			
			var currentNode = zTreeObj.getNodeByParam(key, value);
			
			// 回显数据到表单
			$("#removeNodeSpan").html(" 【 <i class='"+currentNode.icon+"'></i> "+currentNode.name+"】");
			
			return false;
		});
		
		// 模态框确认删除按钮绑定单击事件
		$("#confirmBtn").click(function(){
			// ajax请求
			$.ajax({
				"url" : "menu/remove.json",
				"type" : "post",
				"data" : {
					"id" : window.id
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
						
						// 重新加载树形结构
						generateTree();
					}
				},
				"error" : function(response){
					layer.msg("错误：" + response.staus + " " + response.statusText);
				}
			});
			$("#menuConfirmModal").modal("hide");
			
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
						<i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<!-- 这个ul标签是zTree动态生成的节点所依附的静态节点 -->
						<!-- ztree第三方库必须的标签 -->
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>

	</div>
	
	<!-- 引入模态框的页面 -->
	<%@ include file="/WEB-INF/modal-menu-add.jsp"  %>
	<%@ include file="/WEB-INF/modal-menu-edit.jsp"  %>
	<%@ include file="/WEB-INF/modal-menu-confirm.jsp"  %>
</body>
</html>
