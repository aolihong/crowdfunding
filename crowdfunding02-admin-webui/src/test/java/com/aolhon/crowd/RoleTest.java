package com.aolhon.crowd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aolhon.crowd.entity.Role;
import com.aolhon.crowd.mapper.RoleMapper;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月2日 下午4:34:51
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", 
								   "classpath:spring-persist-tx.xml"})
public class RoleTest {
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Test
	public void testAddRoles(){
		for (int i = 0; i < 160; i++) {
			roleMapper.insert(new Role(null, "name" + (i+1)));
		}
	}
}
