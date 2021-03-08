/* *********************************修改权限************************************ */

// 声明专门的函数用来在分配 Auth 的模态框中显示 Auth 的树形结构数据 
function fillAuthTree() {
	

	// 1.发送 Ajax 请求查询 Auth 数据
	var ajaxReturn = $.ajax({
		"url" : "assgin/get/all/auth.json",
		"type" : "post",
		"dataType" : "json",
		"async" : false
	});

	if (ajaxReturn.status != 200) {
		layer.msg(" 请 求 处 理 出 错 ！ 响 应 状 态 码 是 ： " + ajaxReturn.status
				+ " 说 明 是 ： " + ajaxReturn.statusText);
		return;

	}

	// 2.从响应结果中获取 Auth 的 JSON 数据
	// 从服务器端查询到的 list 不需要组装成树形结构，这里我们交给 zTree 去组装
	var authList = ajaxReturn.responseJSON.data; 
	
	// 3.准备对 zTree 进行设置的 JSON 对象 
	var setting ={ 
			"data": { 
				"simpleData": { 
					// 开启简单 JSON 功能
					"enable": true, 
					
					// 使用categoryId 属性关联父节点，不用默认的 pId 了
					"pIdKey": "categoryId" 
					},
					
				"key": {
					// 使用 title 属性显示节点名称，不用默认的 name 作为属性名了
					"name": "title"
				}
			},
			"check": { 
				"enable": true
			}
	};
	
	// 4.生成树形结构
	
	// <ul id="authTreeDemo" class="ztree"></ul> 
	$.fn.zTree.init($("#authTreeDemo"), setting, authList);
	
	// 获取 zTreeObj 对象 
	var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
	
	// 调用 zTreeObj 对象的方法，把节点展开 
	zTreeObj.expandAll(true);
	
	// 5.查询已分配的 Auth 的 id 组成的数组
	ajaxReturn = $.ajax({
		"url":"assign/get/assigned/auth/id/by/role/id.json", 
		"type":"post", 
		"data":{ 
			"roleId":window.roleId
		},
		"dataType":"json",
		"async":false 
	});
	if(ajaxReturn.status != 200) {
		layer.msg(" 请 求 处 理 出 错 ！ 响 应 状 态 码 是 ： "+ajaxReturn.status+" 说 明 是 ： "+ajaxReturn.statusText); 
		return ; }
	
	// 从响应结果中获取 authIdArray 
	var authIdArray = ajaxReturn.responseJSON.data;
	
	// 6.根据 authIdArray 把树形结构中对应的节点勾选上
	// ①遍历 authIdArray 
	for(var i = 0; i < authIdArray.length; i++) { 
		var authId = authIdArray[i];
		// ②根据 id 查询树形结构中对应的节点 
		var treeNode = zTreeObj.getNodeByParam("id", authId); 
		
		// ③将 treeNode 设置为被勾选 
		
		// checked 设置为 true 表示节点勾选 
		var checked = true; 
		
		// checkTypeFlag 设置为 false，表示不“联动”，不联动是为了避免把不该勾选的勾 选上 
		var checkTypeFlag = false; 
		
		// 执行
		zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
	}
}

/* *********************************删除Role相关函数************************************ */

// 显示删除提示的模态框
function showConfirmModal(roleArray) {
	// 清空模态框中数据
	$("#roleNameDiv").text("");

	// 打开模态框
	$("#confirmModal").modal("show");

	// 在全局变量范围创建数组用来存放角色id
	window.roleIdArray = [];

	// 遍历角色数组，显示在模态框中
	for (var i = 0; i < roleArray.length; i++) {
		// roleId、取出roleName
		var roleId = roleArray[i].roleId;
		var roleName = roleArray[i].roleName;

		// 追加到模态框id="roleNameDiv"的标签中
		$("#roleNameDiv").append(roleName + "<br/>");

		// 调用数组对象的push()方法存入新元素
		window.roleIdArray.push(roleId);
	}
}

/* *********************************分页相关函数************************************ */

// 执行分页，生成页面效果，任何时候加载调用这个函数，都会重新加载页面
function generatePage() {
	// 1.获取分页数据
	var pageInfo = getPageInfoRemote();

	// 2.填充表格
	fillTableBody(pageInfo);
}

// 远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {
	// 调用$.ajax()函数向服务器发送请求，并接受服务器的响应
	var ajaxResult = $.ajax({
		"url" : "role/get/page.json",
		"type" : "post",
		"data" : {
			"pageNum" : window.pageNum,
			"pageSize" : window.pageSize,
			"keyword" : window.keyword
		},
		// 选择为同步方式。因为这几个函数是需要逐步进行的
		"async" : false,
		"dataType" : "json"
	});

	// 打印服务器的响应结果
	console.log(ajaxResult);

	// 判断返回的响应状态码是否为 200（正常响应）
	var statusCode = ajaxResult.status;
	if (statusCode != 200) {
		layer.msg("失败！响应码：" + statusCode + "，说明信息：" + ajaxResult.statusText);
		return null;
	}

	// 取得服务器响应的数据（ResultEntity对象）
	var resultEntity = ajaxResult.responseJSON;

	// 从result中取result属性（这个属性是ResultEntity中封装的）
	var result = resultEntity.result;
	if (result == "FAILED") {
		layer.msg(resultEntity.message);
		return null;
	}

	// 最后取出pageInfo数据(为PageInfo类对象)
	var pageInfo = resultEntity.data;

	return pageInfo;

}
function fillTableBody(pageInfo) {

	// 清除tbody中的旧的内容
	$("#rolePageBody").empty();

	// 这里清空是为了让没有搜索结果时不显示页码导航条
	$("#Pagination").empty();

	// 判断pageInfo对象是否有效
	if (pageInfo == null || pageInfo == undefined || pageInfo.list == null
			|| pageInfo.list.length == 0) {
		$("#rolePageBody").append(
				"<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>");

		return;
	}

	// 使用pageInfo的list属性填充tbody
	for (var i = 0; i < pageInfo.list.length; i++) {

		var role = pageInfo.list[i];

		var roleId = role.id;

		var roleName = role.name;

		var numberTd = "<td>" + (i + 1) + "</td>";
		var checkboxTd = "<td><input id='" + roleId
				+ "' class='itemBox' type='checkbox'></td>";
		var roleNameTd = "<td>" + roleName + "</td>";

		var checkBtn = "<button id='"
				+ roleId
				+ "' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";

		// 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
		var pencilBtn = "<button id='"
				+ roleId
				+ "' type='button' class='btn btn-primary btn-xs updateBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";

		// 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
		var removeBtn = "<button id='"
				+ roleId
				+ "' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

		var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn
				+ "</td>";

		var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd
				+ "</tr>";

		$("#rolePageBody").append(tr);
	}

	// 生成分页导航条
	generateNavigator(pageInfo);
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {

	// 获取总记录数
	var totalRecord = pageInfo.total;

	// 声明相关属性
	var properties = {
		"num_edge_entries" : 3,
		"num_display_entries" : 5,
		"callback" : paginationCallBack,
		"items_per_page" : pageInfo.pageSize,
		"current_page" : pageInfo.pageNum - 1,
		"prev_text" : "上一页",
		"next_text" : "下一页"
	}

	// 调用pagination()函数
	$("#Pagination").pagination(totalRecord, properties);
}

// 翻页时的回调函数。当我们点击翻页按钮时，pagination插件会自动调用此函数
function paginationCallBack(pageIndex, jQuery) {

	// 修改window对象的pageNum属性
	window.pageNum = pageIndex + 1;

	// 调用分页函数
	generatePage();

	// 设置总复选框状态为false
	$("#summaryBox").prop("checked", false);

	// 取消页码超链接的默认行为
	return false;

}