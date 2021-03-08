package com.aolhon.crowd;

import java.sql.SQLException;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.jdbc.Null;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aolhon.crowd.entity.Admin;
import com.aolhon.crowd.mapper.AdminMapper;
import com.aolhon.crowd.service.api.AdminService;

/**
 * @author     aolihong
 * @email      aolhon@163.com
 * @data-time  2021�?1�?21�? 下午9:47:40
 * 
 */

//在进行测试之前，先需要在本项目加入相关的test依赖（junit、spring-test），
//即使在该项目的子工程中的pom.xml中加入了依赖,也需要加入test依赖�?
//因为test依赖只能在本项目中有效，在父工程、子工程中还�?另加入�?�（版本控制依然可以由父工程来控制）

//指定Spring给junit提供的运行器�?
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", 
								   "classpath:spring-persist-tx.xml"})
public class CrowdSpringTest {
	//注入属�??
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AdminMapper adminMapper; 
	
	@Autowired
	private AdminService adminService;
	

	/**
	 * 用来测试Service实现类是否使用上了声明式事物
	 */
	@Test
	public void testTx(){
		adminService.savaAdmin(new Admin(null, "wang", "123456", "wang", "wang@qq.com", null));
	}
	
	@Test
	public void saveTestAdmins(){
		for (int i = 1; i < 161; i++) {
			adminService.savaAdmin(new Admin(null, "loginAcct" + i, i + "", "userName" + i, "email" + i, null));
		}
	}
	
	/**
	 * 用来测试Mapper接口实现类的代理对象是否可以操作数据�?
	 */
	@Test
	public void testAdminMapper(){
		Admin admin = new Admin(1, "aolhon", "123", "向往", "aolhon@163.com", null);
		
		int nums = adminMapper.insert(admin);
		
		System.out.println("影响的行数：" + nums);
	}
	
	/**
	 * 用来测试是否已连接上数据库了
	 */
	@Test
	public void testConnection() throws SQLException {
		java.sql.Connection connection = dataSource.getConnection();
		System.out.println(connection);
		System.out.println("我是junit测试");
	}
	
	@Test
	public void testLog(){
		Log log = LogFactory.getLog(CrowdSpringTest.class);
		
		log.debug("Hello I am Debug level!!!");
		log.debug("Hello I am Debug level!!!");
		log.debug("Hello I am Debug level!!!");
		
		log.info("Info level!!!");
		log.info("Info level!!!");
		log.info("Info level!!!");
		
		log.warn("Warn level!!!");
		log.warn("Warn level!!!");
		log.warn("Warn level!!!");
		
		log.error("Error level!!!");
		log.error("Error level!!!");
		log.error("Error level!!!");
	}
	
	

}
