package com.aolhon.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aolhon.crowd.constant.CrowdConstant;
import com.aolhon.crowd.entity.Admin;
import com.aolhon.crowd.entity.AdminExample;
import com.aolhon.crowd.entity.AdminExample.Criteria;
import com.aolhon.crowd.exception.LoginAcctAlreadyInUseException;
import com.aolhon.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.aolhon.crowd.exception.LoginFailedException;
import com.aolhon.crowd.mapper.AdminMapper;
import com.aolhon.crowd.service.api.AdminService;
import com.aolhon.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021??1??26?? ????10:43:35
 * 
 */
//创建对象（也是把此类加入到IOC容器中）
@Service
public class AdminServiceImpl implements AdminService{

	// 装载属性
	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public void savaAdmin(Admin admin) {
		
		String rawPassword = admin.getUserPswd();
		
		// （1）加密方式一：md5加密
//		String encodedPswd = CrowdUtil.md5(rawPassword);

		// （2）加密方式二：BCrypt加密（带盐值）
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		String encodedPswd = bCryptPasswordEncoder.encode(rawPassword);
		
		admin.setUserPswd(encodedPswd);
		
		// 创建时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = dateFormat.format(date);
		
		admin.setCreateTime(currentDate);
		
		// 当重复保存loginAdmin时，adminMapper.insert(admin)会抛出DuplicateKeyException异常，
		// 因为loginAcct在数据库中是UNIQUE字段约束（唯一约束）的
		try {
			// 保存admin
			adminMapper.insert(admin);			
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseException("当前登陆账号已存在");
			}
		}
		
		// 手动模拟算数异常
//		int i = 10 / 0;
		
	}

	@Override
	public List<Admin> getAll() {
		// TODO Auto-generated method stub
		return adminMapper.selectByExample(new AdminExample());
	}

	@Override
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
		// 1. 通过loginAcct得到admin对象
			// 1.1 创建AdminExample对象
		AdminExample adminExample = new AdminExample();
		
			// 1.2 创建Criteria对象
		Criteria criteria = adminExample.createCriteria();
		
			// 1.3 ??Criteria????????????????
		criteria.andLoginAcctEqualTo(loginAcct);
		/* 此方法相当于select * from t_admin where LoginAcct = 'loginAcct'
		 * XxxExample只是用做sql语句中where添加的查询条件
		 * XxxMapper还是用来查询字段数据的
		 * */
		
			// 1.4 ????AdminMapper??????????
		List<Admin> list = adminMapper.selectByExample(adminExample);
		
		// 2.判断admin有效性，若为null，则抛出自定义的登陆失败异常
		if(list == null || list.size() == 0){
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		if(list.size() > 1){
			throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
		}
		
		//取得admin对象
		Admin admin = list.get(0);
		
		// 3.??????????admin??????????
		if(admin == null){
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// ??????????????????
		// 4.???admin????????(????)
		String userPswdDB = admin.getUserPswd();
		
		// 5.??????????????????
		String userPswdForm = CrowdUtil.md5(userPswd);
		
		// 6.????????????????????????Objects???????????????????????????????equals()??????
		boolean b = Objects.equals(userPswdDB, userPswdForm);
		
		// 7.????????????????
		if (!b) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		return admin;
	}

	@Override
	public PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize) {
		// 1.开启分页功能
		// startPage(1, 6):获取第1页的6条内容
		PageHelper.startPage(pageNum, pageSize);
		
		// 2.查询Admin数据
		List<Admin> adminList = adminMapper.selectAdminListByKeyword(keyword);
		
		// 3.把adminList数据封装成PageInfo（方便使用）
		PageInfo<Admin> pageInfo = new PageInfo<>(adminList);
		
		return pageInfo;
		
	}

	@Override
	public void removeAdmin(Integer adminId) {
		adminMapper.deleteByPrimaryKey(adminId);
	}

	@Override
	public Admin getAdminByPrimaryKey(Integer adminId) {
		// 查询对应id的admin对象
		Admin admin = adminMapper.selectByPrimaryKey(adminId);
		
		return admin;
	}

	@Override
	public void updateAdmin(Admin admin) {
		
		// 对密码进行md5加密
		String userPswd = admin.getUserPswd();
		userPswd = CrowdUtil.md5(userPswd);
		admin.setUserPswd(userPswd);
		
		// 选择性的更新,admin数据里为空值的不更新
		// 当loginAcct已有时，这里会抛出异常，因为loginAdmin字段的唯一约束
		try{
			adminMapper.updateByPrimaryKeySelective(admin);			
		} catch(Exception e) {
			if(e instanceof DuplicateKeyException){
				throw new LoginAcctAlreadyInUseForUpdateException("当前账户名称已存在，请使用其他账户名称");
			}

		}
		
		
		
	}

	@Override
	public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
		// 为了简化操作：先根据 adminId 删除旧的数据，再根据 roleIdList 保存全部新的数据 
		
		// 1.根据 adminId 删除旧的关联关系数据 
		adminMapper.deleteOLdRelationship(adminId); 
		
		// 2.根据 roleIdList 和 adminId 保存新的关联关系 
		if(roleIdList != null && roleIdList.size() > 0) { 
			adminMapper.insertNewRelationship(adminId, roleIdList); 
			
		}
		
	}

	@Override
	public Admin getAdminByLoginAcct(String username) {
		// 1.增加where筛选条件loginAcct=username
		AdminExample adminExample = new AdminExample();
		Criteria criteria = adminExample.createCriteria();
		criteria.andLoginAcctEqualTo(username);
		
		// 2.带筛选条件查询
		List<Admin> adminList = adminMapper.selectByExample(adminExample);
		Admin admin = adminList.get(0);
		
		return admin;
	}
}
