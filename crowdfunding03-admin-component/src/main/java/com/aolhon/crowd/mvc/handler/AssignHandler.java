package com.aolhon.crowd.mvc.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aolhon.crowd.entity.Auth;
import com.aolhon.crowd.entity.Role;
import com.aolhon.crowd.service.api.AdminService;
import com.aolhon.crowd.service.api.AuthService;
import com.aolhon.crowd.service.api.RoleService;
import com.aolhon.crowd.util.ResultEntity;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月5日 下午4:38:17
 * 角色分配的控制器
 */
@Controller
public class AssignHandler {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AuthService authService;
	
	@ResponseBody
	@RequestMapping("/assign/do/role/assign/auth.json")
	public ResultEntity<String> saveRoleAssignAuth(
			@RequestBody Map<String, List<Integer>> map){
		
		authService.saveRoleAssignAuth(map);
		
		return ResultEntity.successWithoutData();
	}

	@ResponseBody
	@RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
	public ResultEntity<List<Integer>> getAssignAuthIdByRoleId(
			@RequestParam("roleId") Integer roleId){
		
		// 查询已经分配好权限的角色
		List<Integer> authList =  authService.getAssignedAuthIdByRoleId(roleId);
		
		return ResultEntity.successWithData(authList);
	}
	
	
	@ResponseBody
	@RequestMapping("/assgin/get/all/auth.json")
	public ResultEntity<List<Auth>> getAllAuth(){
		List<Auth> authList = authService.getAllAuth();
		
		return ResultEntity.successWithData(authList);
	}
	
	/**
	 * 保存分配好的角色
	 * @param adminId
	 * @param pageNum
	 * @param keyword
	 * @param roleIdList
	 * @return
	 */
	@RequestMapping("/assign/do/role/assign.html")
	public String saveAdminRoleRelationship(
			@RequestParam("adminId") Integer adminId,
			@RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyword,
			@RequestParam(value="roleIdList", required=false) List<Integer> roleIdList){
			
		adminService.saveAdminRoleRelationship(adminId, roleIdList);
		return  "redirect:/admin/get/page.html" +
				"?keyword=" + keyword + 
				"&pageNum=" + pageNum;
	}
	
	/**
	 * 得到每个管理员对应的角色
	 * @param adminId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/assign/to/assign/role/page.html")
	public String toAssignRolePage(
			@RequestParam("adminId") Integer adminId,
			ModelMap modelMap){
		
		// 查询已分配角色
		List<Role> assignedRoleList = roleService.getAssignRole(adminId);
		// 查询未分配角色
		List<Role> unAssignedRoleList = roleService.getUnAssignRole(adminId);
		
		// 想request域中添加分配角色的信息
		modelMap.addAttribute("assignedRoleList", assignedRoleList);
		modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
		
		return "assign-role";
	}
}
