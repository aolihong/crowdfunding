<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="assignModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">修改权限</h4>
			</div>
			<div class="modal-body">			
				<!-- 这个ul标签是zTree动态生成的节点所依附的静态节点 -->
				<!-- ztree第三方库必须的标签 -->
				<ul id="authTreeDemo" class="ztree"></ul>
			</div>
			<div class="modal-footer">
				<button id="assignAuthBtn" type="button" class="btn btn-success">执行分配</button>
			</div>
		</div>
	</div>
</div>
