package com.aolhon.crowd.service.api;

import java.util.List;

import com.aolhon.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月2日 下午3:07:22
 * 
 */
public interface RoleService {

	/**
	 * 得到分页好的role数据list集合
	 * @param pageNum
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	PageInfo<Role> getRolePage(Integer pageNum, Integer pageSize, String keyword);

	/**
	 * 添加一个role角色
	 */
	void addRole(Role role);

	/**
	 * 修改一个角色名称
	 * @param role
	 */
	void updateRole(Role role);

	/**
	 * 删除集合里的角色
	 * @param roleIdList
	 */
	void removeRole(List<Integer> roleIdList);

	/**
	 * 查询已分配角色
	 * @param adminId
	 * @return
	 */
	List<Role> getAssignRole(Integer adminId);

	/**
	 * 查询未分配角色
	 * @param adminId
	 * @return
	 */
	List<Role> getUnAssignRole(Integer adminId);



}
