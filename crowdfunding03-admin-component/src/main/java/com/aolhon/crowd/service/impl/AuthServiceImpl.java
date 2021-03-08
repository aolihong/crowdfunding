package com.aolhon.crowd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aolhon.crowd.entity.Auth;
import com.aolhon.crowd.entity.AuthExample;
import com.aolhon.crowd.mapper.AuthMapper;
import com.aolhon.crowd.service.api.AuthService;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月6日 下午8:50:23
 * 
 */
@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthMapper authMapper;


	@Override
	public List<Auth> getAllAuth() {
		 List<Auth> AuthList = authMapper.selectByExample(new AuthExample());
		return AuthList;
	}

	@Override
	public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
		return authMapper.selectAssignedAuthIdByRoleId(roleId);
	}

	@Override
	public void saveRoleAssignAuth(Map<String, List<Integer>> map) {

		// （1）删除原先此角色的所有权限
		
		// 获取roleId
		List<Integer> roleIdList = map.get("roleId");
		Integer roleId = roleIdList.get(0);
		
		// 删除
		authMapper.deleteOldRelationship(roleId);
		
		// (2)保存新的角色权限
		List<Integer> authIdList = map.get("authIdArray");
		if(authIdList != null && authIdList.size() > 0){
			authMapper.insertNewRelationship(roleId ,authIdList);
		}
		
	}

	@Override
	public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
		List<String> assignAuthNameList = authMapper.selectAssignedAuthNameByAdminId(adminId);
		return assignAuthNameList;
	}
}
