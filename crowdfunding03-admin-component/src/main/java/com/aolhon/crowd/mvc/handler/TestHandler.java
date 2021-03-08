//package com.aolhon.crowd.mvc.handler;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.aolhon.crowd.entity.Admin;
//import com.aolhon.crowd.entity.Student;
//import com.aolhon.crowd.service.api.AdminService;
//import com.aolhon.crowd.util.ResultEntity;
//
///**
// * @author   aolihong
// * @email    aolhon@163.com
// * @time     2021??1??26?? 下午6:17:58
// * 
// */
//@Controller
//public class TestHandler {
//	
//	@Autowired
//	private AdminService adminService;
//	
//	private Logger logger = LoggerFactory.getLogger(TestHandler.class);
//
//	
//	/**
//	 * 	请求分为两种：普通请求???ajax请求（主要是针对于请求数据???响应数据的封装方式不同??
//	 *	??1）普通请求：返回??个页面作为新的数据页??
//	 *	??2）ajax请求：把控制器方法返回???封装成json数据，jQuery使用json数据来对页面??部更??
//	 *
//	 *	??般为ajax请求，处理器方法都需要使用@ResponseBody注解
//	 *	@ResponseBody:让处理器方法的返回???本身就是当前请求的响应数据，不再参考内部资源视图解析器的前??后缀??
//	 * 
//	 */
//	@ResponseBody
//	@RequestMapping("jsonObject.json")
//	public ResultEntity<Student> testJsonObject(@RequestBody Student student, HttpServletRequest request){
//		logger.info(student.toString());
//		
//		//疑问：ResultEntity的静态方法没有指定泛型，为什么可以正常使用？
//		ResultEntity<Student> resultEntity = ResultEntity.successWithData(student);
//		
//		//模拟算数异常
////		int i = 10 / 0;
//
//		//模拟空指针异??
//		String str = null;
//		System.out.println(str.length());
//		
//		return resultEntity;			
//
//	}
//	
//	/**
//	 * 测试SSM搭建的环??
//	 * @param modelMap
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/test/ssm.html")
//	public String testSsm(ModelMap modelMap, HttpServletRequest request){
//		
//		
//		List<Admin> adminList = adminService.getAll();
//		
//		modelMap.addAttribute("adminList", adminList);
//		
////		int i = 10 / 0;
//		
//		return "target";
//	}
//	
//}
