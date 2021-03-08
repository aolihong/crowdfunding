function generateTree(){
	// 先发送ajax请求，才会有后续的响应数据显示
	$.ajax({
		"url" : "menu/get/whole/tree.json",
		"type" : "post",
		"dataType" : "json",
		"success" : function(response){
			// 取得ResultEntity类对象result属性
			var result = response.result;
			if(result == "FAILED"){
				layer.msg("操作失败！" + response.message);
			}
			
			if(result == "SUCCESS"){
				layer.msg("操作成功！")

				var setting = {
					"view" : {
						"addDiyDom" : myAddDiyDom,
						"addHoverDom": myAddHoverDom,
						"removeHoverDom": myRemoveHoverDom
					},
					"data" : { 
						"key": { 
							"url": "maomi" 
						} 
					}
					
				}
			
				// 获得树形结构的数据
				var zNodes = response.data;	
				
				// 初始化树形结构
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			}
		},
		"error" : function(response) {
			layer.msg("错误" + response.status + " " + response.statusText);
		}
	});
	
}


// 在鼠标离开节点范围时删除按钮组
function myRemoveHoverDom(treeId, treeNode) {
	
	// 拼接按钮组的id
	var btnGroupId = treeNode.tId + "_btnGrp";
	
	// 移除对应的元素
	$("#"+btnGroupId).remove();
	
}



// 在鼠标移入节点范围时添加按钮组
function myAddHoverDom(treeId, treeNode) {
	
	// 按钮组的标签结构：<span><a><i></i></a><a><i></i></a></span>
	// 按钮组出现的位置：节点中treeDemo_n_a超链接的后面
	
	var editBtn = "<a id="+treeNode.id+" class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='修改'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
	var removeBtn = "<a id="+treeNode.id+" class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;'  title='删除'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
	var addBtn = "<a id="+treeNode.id+" class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='添加'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
	
	// 获得当前节点的级别数据
	var level = treeNode.level;
	
	// 拼接的按钮html代码
	var btnHTML = "";
	
	// 判断节点级别（不同的节点有不同的按钮）
	// 根节点
	if(level == 0){
		btnHTML = addBtn;
	}
	// 分支节点
	if(level == 1){
		btnHTML = addBtn + " " + editBtn;
		
		var length = treeNode.children.length;
		// 没有子节点的分支节点
		if(length == 0){
			btnHTML = btnHTML + " " + removeBtn;
		}
	}
	
	// 叶子节点
	if(level == 2){
		btnHTML = editBtn + " " + removeBtn;
	}
	
	// 为了在需要移除按钮组的时候能够精确定位到按钮组所在span，需要给span设置有规律的id
	var btnGroupId = treeNode.tId + "_btnGrp";

	// 判断一下以前是否已经添加了按钮组
	if($("#"+btnGroupId).length > 0) {
		return ;
	}

	// 找到附着按钮组的超链接
	var anchorId = treeNode.tId + "_a";
	
	// 执行在超链接后面附加span元素的操作
	$("#"+anchorId).after("<span id='" + btnGroupId + "'>" + btnHTML + "</span>");

}
	
// 修改默认的图标
function myAddDiyDom(treeId, treeNode) {
	// treeId是整个树形结构附着的ul标签的id
	console.log("treeId="+treeId);
	
	// 当前树形节点的全部的数据，包括从后端查询得到的Menu对象的全部属性
	console.log(treeNode);
	
	// zTree生成id的规则
	// 例子：treeDemo_7_ico
	// 解析：ul标签的id_当前节点的序号_功能
	// 提示：“ul标签的id_当前节点的序号”部分可以通过访问treeNode的tId属性得到
	// 根据id的生成规则拼接出来span标签的id
	var spanId = treeNode.tId + "_ico";
	
	// 根据控制图标的span标签的id找到这个span标签
	// 删除旧的class
	// 添加新的class
	$("#"+spanId)
		.removeClass()
		.addClass(treeNode.icon);
}