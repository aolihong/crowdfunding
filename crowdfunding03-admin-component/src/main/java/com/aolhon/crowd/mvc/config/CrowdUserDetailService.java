package com.aolhon.crowd.mvc.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.aolhon.crowd.entity.Admin;
import com.aolhon.crowd.entity.Role;
import com.aolhon.crowd.service.api.AdminService;
import com.aolhon.crowd.service.api.AuthService;
import com.aolhon.crowd.service.api.RoleService;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月10日 上午10:50:28
 * 作为数据库登陆情景时所需的数据信息（登陆账号、登陆密码、角色、权限……）
 */
// 加载到SpringMVC的IOC容器中
@Component
public class CrowdUserDetailService implements UserDetailsService{
	
	@Autowired
	private AuthService AuthService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AdminService adminService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 1.获得Admin对象（登陆账号、登陆密码）
		Admin admin = adminService.getAdminByLoginAcct(username);
		
		Integer adminId = admin.getId();
		
		// 2.获得adminId对应的角色集合
		List<Role> assignRoleNamelist = roleService.getAssignRole(adminId);
		
		// 3.获得adminId对应的权限集合（注意库表之间关系）
		List<String> assignAuthNameList =  AuthService.getAssignedAuthNameByAdminId(adminId);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		// 设置角色
		for (Role role : assignRoleNamelist) {
			String roleName = "ROLE_" + role.getName();
			
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
			
			authorities.add(simpleGrantedAuthority);
		}
		// 设置权限
		for (String authName : assignAuthNameList) {
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
			authorities.add(simpleGrantedAuthority);
		}
		
		SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
		
		return securityAdmin;
	}
	
	 

}
