package com.aolhon.crowd.service.api;

import java.util.List;
import java.util.Map;

import com.aolhon.crowd.entity.Auth;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月6日 下午8:49:52
 * 
 */
public interface AuthService {


	/**
	 * 得到所有角色权限
	 * @return
	 */
	public List<Auth> getAllAuth();


	/**
	 * 查询已经分配好权限的角色
	 * @param roleId
	 * @return
	 */
	public List<Integer> getAssignedAuthIdByRoleId(Integer roleId);


	/**
	 * 保存分配好的权限
	 * @param map
	 */
	public void saveRoleAssignAuth(Map<String, List<Integer>> map);


	/**
	 * 通过adminId得到对应权限
	 * @param adminId
	 * @return
	 */
	public List<String> getAssignedAuthNameByAdminId(Integer adminId);
	
}
