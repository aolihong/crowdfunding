package com.aolhon.crowd.service.api;

import java.util.List;

import com.aolhon.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年1月26日 上???10:12:51
 * 
 */
public interface AdminService {
	
	/**
	 * 保存一个管理员
	 * @param admin
	 * @return
	 */
	public void savaAdmin(Admin admin);

	/**
	 * 获得所有管理员信息
	 * @return
	 */
	public List<Admin> getAll();

	/**
	 * 查询某个admin信息
	 * @param loginAcct
	 * @param userPswd
	 * @return
	 */
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd);
	
	/**
	 * 对数据库admin数据进行分页
	 * @param keyword 关键字
	 * @param pageNum 页码
	 * @param pageSize 每页数量
	 * @return
	 */
	public PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize);

	/**
	 * 通过管理员ID删除指定管理员
	 * @param adminId
	 */
	public void removeAdmin(Integer adminId);

	/**
	 * 通过主键id查询对应的管理员
	 * @param adminId
	 * @return
	 */
	public Admin getAdminByPrimaryKey(Integer adminId);

	/**
	 * 更新admin对象数据
	 */
	public void updateAdmin(Admin admin);

	/**
	 * 保存管理员角色分配信息
	 * @param adminId
	 * @param roleIdList
	 */
	public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

	/**
	 * 通过登陆账号获得admin对象
	 * @param username
	 */
	public Admin getAdminByLoginAcct(String username);
	
}
