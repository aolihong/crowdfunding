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
// * @time     2021??1??26?? ����6:17:58
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
//	 * 	�����Ϊ���֣���ͨ����???ajax������Ҫ���������������???��Ӧ���ݵķ�װ��ʽ��ͬ??
//	 *	??1����ͨ���󣺷���??��ҳ����Ϊ�µ�����ҳ??
//	 *	??2��ajax���󣺰ѿ�������������???��װ��json���ݣ�jQueryʹ��json��������ҳ��??����??
//	 *
//	 *	??��Ϊajax���󣬴�������������Ҫʹ��@ResponseBodyע��
//	 *	@ResponseBody:�ô����������ķ���???������ǵ�ǰ�������Ӧ���ݣ����ٲο��ڲ���Դ��ͼ��������ǰ??��׺??
//	 * 
//	 */
//	@ResponseBody
//	@RequestMapping("jsonObject.json")
//	public ResultEntity<Student> testJsonObject(@RequestBody Student student, HttpServletRequest request){
//		logger.info(student.toString());
//		
//		//���ʣ�ResultEntity�ľ�̬����û��ָ�����ͣ�Ϊʲô��������ʹ�ã�
//		ResultEntity<Student> resultEntity = ResultEntity.successWithData(student);
//		
//		//ģ�������쳣
////		int i = 10 / 0;
//
//		//ģ���ָ����??
//		String str = null;
//		System.out.println(str.length());
//		
//		return resultEntity;			
//
//	}
//	
//	/**
//	 * ����SSM��Ļ�??
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
