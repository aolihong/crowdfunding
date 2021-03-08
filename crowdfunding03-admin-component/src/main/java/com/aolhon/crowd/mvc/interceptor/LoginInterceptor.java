package com.aolhon.crowd.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aolhon.crowd.constant.CrowdConstant;
import com.aolhon.crowd.entity.Admin;
import com.aolhon.crowd.exception.AccessForbiddenException;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021��1��31�� ����12:47:03
 * ��Ϊ??����½������ʹ�ã�ָ��ĳЩҳ��������Ҫ�ڴ���������֮ǰ����һ�����ؼ�??
 * 
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

	/**
	 * �������session��������Ƿ����admin���ݣ����з��У���������
	 */
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		//ȡ��session������е�admin����
		HttpSession session = request.getSession();
		Admin admin = (Admin)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
		
		//��admin�����ж�
		if (admin == null) {
			throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);			
		}
		
		//admin��Ϊnull����??
		return true; 
	}

}
