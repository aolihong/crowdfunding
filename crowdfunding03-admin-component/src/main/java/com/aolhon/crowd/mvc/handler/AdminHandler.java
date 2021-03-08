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
 * @time     2021��1��30�� ����9:13:53
 * 
 * ��Ϊ����Ա��½��������Ŀ�����
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
	 * �õ�ָ��id��admin����
	 * @param adminId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/admin/to/edit/page.html")
	public String toEditPage(
			@RequestParam("adminId") Integer adminId,
			ModelMap modelMap){
		
		// ͨ��adminId��ѯ����Ӧ��admin����
		Admin admin = adminService.getAdminByPrimaryKey(adminId);
		
		modelMap.addAttribute("admin", admin);
		
		return "admin-edit";
	}
	
	/**
	 * ��ӹ���Ա����Ŀ���
	 * @param admin
	 * @return
	 */
	// ���Ȩ�޷��ʿ���
	@PreAuthorize("hasAuthority('user:save')")
	@RequestMapping("/admin/add.html")
	public String addAdmin(Admin admin){
		adminService.savaAdmin(admin);
		
		// �ض�����ӵ��Ĺ���Ա�б�ҳ(�������һҳ)
		return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
	}
	
	/**
	 * ͨ��id��ɾ��ָ���Ĺ���Ա
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
		
		// ͨ��adminIdɾ��ָ���Ĺ���Ա
		adminService.removeAdmin(adminId);
		
		// �ض�����ͨ�������������ص�����ҳ��
		return "redirect:/admin/get/page.html" +
				"?keyword=" + keyword + 
				"&pageNum=" + pageNum;
				
	}
	
	/**
	 * ��ҳ���ܵ��������������
	 * @param keyword
	 * @param pageNum
	 * @param pageSize
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/admin/get/page.html")
	public String getAdminPage(
			// @RequestParamע���Ĭ��ֵdefaultValue����ʹ��
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
			ModelMap modelMap){
		
		// �õ��Ѿ�ͨ��pageNum��pageSize������ҳ�õ�admin����
		PageInfo<Admin> adminPageInfo = adminService.getAdminPage(keyword, pageNum, pageSize);
		
		// modelMap.addAttribute()�൱��request.setAttribute()
		// ������ǰ��ҳ�洫������
		modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, adminPageInfo);
		
		// ��Ϊ�߼���ͼ��������ת(��������ת�������ɹ���ת��Ŀ��ҳ��󣬵�һ�������еĲ�����Ȼ��Ч)
		return "admin-page";
	}
	
	
	/**
	 * ??����½����Ĵ�������??
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/do/logout.html")
	public String doLogout(HttpSession session){
		//ʹsession������е�����ǿ��ʧ??
		session.invalidate();
		
		return "admin-login";
	}
	
	
	/**
	 * ��½����Ĵ���������
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
		
		// 1.����adminService��������ö�ӦloginAcct�Ĺ���Ա��Ϣ
		Admin loginAdmin = adminService.getAdminByLoginAcct(loginAcct, userPswd); 
		
		// 2.��admin���ݴ洢��session���У���Ϊ��ǰ��ҳ������Ҫ��ʾ����Ա����??
		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, loginAdmin);
		
		// ����Ӧ��ע������ת�����ض����ѡ��
		/* ��Ϊ���γ�Ϊ����ת�������ɹ���ת������ԱAdminҳ��ʱ��������Ϸ��ĵ�ַ���ǵ�һ��������Ǹ���ַ
		        Ϊadmin/do/login.html����ʱ���������ҳ��ˢ��ʱ������admin/do/login.html���������������Ӹ��أ�
		        ??��Ҫ�����Ż���ʹ���ض���ʹ��½�ɹ���ĵ�ַ�������仯��������֮ǰ�������ַ����Ϊ�ض��������������
		*/
		return "redirect:/admin/to/main/page.html";
		
		//����ĺ�׺ʹ��html��˵�����ػ��ǻᾭ�����������
	}
	
}
