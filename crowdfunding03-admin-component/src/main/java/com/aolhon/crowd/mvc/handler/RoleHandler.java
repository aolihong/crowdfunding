package com.aolhon.crowd.mvc.handler;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aolhon.crowd.entity.Role;
import com.aolhon.crowd.service.api.RoleService;
import com.aolhon.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月2日 下午3:05:29
 * 
 * 角色相关数据请求的控制器
 */
@Controller
public class RoleHandler {
	
	@Autowired
	private RoleService roleService;
	
	@ResponseBody
	@RequestMapping("/role/remove/by/role/id/array.json")
	public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList){
		
		// 删除role
		roleService.removeRole(roleIdList);

		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 更新角色名称
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("role/update.json")
	public ResultEntity<String> updateRole(Role role){
		// 修改role
		roleService.updateRole(role);
		
		return ResultEntity.successWithoutData(); 
	}
	
	/**
	 * 添加一个角色
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("role/add.json")
	public ResultEntity<String> addRole(Role role){
		// 执行增加role
		roleService.addRole(role);
		
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 得到已经分页好并封装好的ResultEntity<PageInfo<Role>>数据,返回json数据
	 * @param pageNum
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	// @ResponseBody注解可以使控制器方法返回一个封装好的json数据对象
	
	// 添加“部长”角色访问控制
	@PreAuthorize("hasRole('部长')")
	@ResponseBody
	@RequestMapping("/role/get/page.json")
	public ResultEntity<PageInfo<Role>> getRolePage(
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
			@RequestParam(value = "keyword", defaultValue = "") String keyword){
		
		// 查询role数据
		PageInfo<Role> roleList = roleService.getRolePage(pageNum, pageSize, keyword);
		
		return ResultEntity.successWithData(roleList);
	}
}
