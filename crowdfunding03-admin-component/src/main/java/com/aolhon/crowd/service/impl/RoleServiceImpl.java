package com.aolhon.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aolhon.crowd.entity.Role;
import com.aolhon.crowd.entity.RoleExample;
import com.aolhon.crowd.entity.RoleExample.Criteria;
import com.aolhon.crowd.mapper.RoleMapper;
import com.aolhon.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月2日 下午3:07:56
 * 
 */
@Service
public class RoleServiceImpl implements RoleService{
	
	// 装载属性
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public PageInfo<Role> getRolePage(Integer pageNum, Integer pageSize, String keyword) {
		
		// 开启分页功能（非侵入式开启）
		PageHelper.startPage(pageNum, pageSize);
		
		// 通过关键字查询
		List<Role> roleList =  roleMapper.getPageListByKeyword(keyword);
	
		// 对roleList进行封装为PageInfo, PageInfo类中有关于分页的数据
		PageInfo<Role> pageInfo = new PageInfo<>(roleList);
		
		return pageInfo;
	}

	@Override
	public void addRole(Role role) {
		roleMapper.insert(role);
	}

	@Override
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}

	@Override
	public void removeRole(List<Integer> roleIdList) {
		
		
		RoleExample roleExample = new RoleExample();
		Criteria criteria = roleExample.createCriteria();
		
		// 添加筛选条件
		criteria.andIdIn(roleIdList);
		
		roleMapper.deleteByExample(roleExample);
	}

	@Override
	public List<Role> getAssignRole(Integer adminId) {
		return roleMapper.selectAssignRole(adminId);
	}

	@Override
	public List<Role> getUnAssignRole(Integer adminId) {
		return roleMapper.selectUnAssignRole(adminId);
	}

	
}
