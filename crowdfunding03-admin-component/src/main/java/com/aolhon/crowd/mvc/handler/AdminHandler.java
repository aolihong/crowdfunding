package com.aolhon.crowd.mvc.handler;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aolhon.crowd.constant.CrowdConstant;
import com.aolhon.crowd.entity.Admin;
import com.aolhon.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年1月30日 下午9:13:53
 * 
 * 作为管理员登陆界面请求的控制器
 */
@Controller
public class AdminHandler {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/admin/edit.html")
	public String updateAdmin(Admin admin){
		adminService.updateAdmin(admin);
		
		String msg = "msg";
	
		return "redirect:/admin/to/edit/page.html?adminId=" + admin.getId() + "&msg=" + msg;
	}
	
	/**
	 * 得到指定id的admin对象
	 * @param adminId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/admin/to/edit/page.html")
	public String toEditPage(
			@RequestParam("adminId") Integer adminId,
			ModelMap modelMap){
		
		// 通过adminId查询到对应的admin对象
		Admin admin = adminService.getAdminByPrimaryKey(adminId);
		
		modelMap.addAttribute("admin", admin);
		
		return "admin-edit";
	}
	
	/**
	 * 添加管理员请求的控制
	 * @param admin
	 * @return
	 */
	// 添加权限访问控制
	@PreAuthorize("hasAuthority('user:save')")
	@RequestMapping("/admin/add.html")
	public String addAdmin(Admin admin){
		adminService.savaAdmin(admin);
		
		// 重定向到添加到的管理员列表页(就是最后一页)
		return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
	}
	
	/**
	 * 通过id来删除指定的管理员
	 * @param adminId
	 * @param pageNum
	 * @param keyword
	 * @param session
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
	public String removeAdmin(
			@PathVariable("adminId") Integer adminId,
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("keyword") String keyword,
			HttpSession session,
			ModelMap modelMap){
		
		// 通过adminId删除指定的管理员
		adminService.removeAdmin(adminId);
		
		// 重定向在通过控制器方法回到请求页面
		return "redirect:/admin/get/page.html" +
				"?keyword=" + keyword + 
				"&pageNum=" + pageNum;
				
	}
	
	/**
	 * 分页功能的请求控制器方法
	 * @param keyword
	 * @param pageNum
	 * @param pageSize
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/admin/get/page.html")
	public String getAdminPage(
			// @RequestParam注解的默认值defaultValue属性使用
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
			ModelMap modelMap){
		
		// 得到已经通过pageNum、pageSize条件分页好的admin数据
		PageInfo<Admin> adminPageInfo = adminService.getAdminPage(keyword, pageNum, pageSize);
		
		// modelMap.addAttribute()相当于request.setAttribute()
		// 用来向前端页面传递数据
		modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, adminPageInfo);
		
		// 作为逻辑试图名请求跳转(属于请求转发，当成功跳转到目的页面后，第一次请求中的参数依然有效)
		return "admin-page";
	}
	
	
	/**
	 * ??出登陆请求的处理器方??
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/do/logout.html")
	public String doLogout(HttpSession session){
		//使session域对象中的数据强制失??
		session.invalidate();
		
		return "admin-login";
	}
	
	
	/**
	 * 登陆请求的处理器方法
	 * @param loginAcct
	 * @param userPswd
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/do/login.html")
	public String doLogin(
			@RequestParam("loginAcct") String loginAcct,
			@RequestParam("userPswd") String userPswd,
			HttpSession session){
		
		// 1.调用adminService方法来获得对应loginAcct的管理员信息
		Admin loginAdmin = adminService.getAdminByLoginAcct(loginAcct, userPswd); 
		
		// 2.把admin数据存储到session域中，因为在前端页面中需要显示管理员的信??
		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, loginAdmin);
		
		// 这里应该注意请求转发与重定向的选择
		/* 因为若次吃为请求转发，当成功跳转到管理员Admin页面时，浏览器上方的地址还是第一次请求的那个地址
		        为admin/do/login.html，此时浏览器进行页面刷新时，还是admin/do/login.html的请求，这样会增加负载，
		        ??以要进行优化，使用重定向。使登陆成功后的地址栏发生变化，不再是之前的请求地址，因为重定向就是两次请求
		*/
		return "redirect:/admin/to/main/page.html";
		
		//这里的后缀使用html，说明返回还是会经过中央调度器
	}
	
}
