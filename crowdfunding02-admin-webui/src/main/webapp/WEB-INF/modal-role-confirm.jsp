<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="confirmModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">删除角色</h4>
			</div>
			<div class="modal-body">
				<form class="form-signin" role="form">
					<h4>删除角色</h4>
					<!-- 角色名称使用jQery动态添加 -->
				<div id="roleNameDiv" style="text-align: center;"></div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="removeRoleBtn" type="button" class="btn btn-danger">确认删除</button>
			</div>
		</div>
	</div>
</div>
