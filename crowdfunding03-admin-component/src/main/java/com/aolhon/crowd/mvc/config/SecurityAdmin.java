package com.aolhon.crowd.mvc.config;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.aolhon.crowd.entity.Admin;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月10日 下午5:49:13
 * 
 */
public class SecurityAdmin extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Admin originalAdmin;
	
	// 创建构造器，并向父类构造器传参	
	public SecurityAdmin(
			// 传入原始的admin对象
			Admin originalAdmin,
			
			// 角色、权限的集合
			List<GrantedAuthority> grantedAuthorities){
		super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), grantedAuthorities);
		
		this.originalAdmin = originalAdmin;
		
		// 擦出密码，提高安全性（账号密码已经赋值到父类中，SecurityAdmin子类对账号密码已经没有用途）
		//this.originalAdmin.setUserPswd(null);
	}
	
	public Admin getOriginalAdmin(){
		return originalAdmin;
	}
}
