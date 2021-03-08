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
 * @time     2021年1月31日 下午12:47:03
 * 作为??个登陆拦截器使用，指定某些页面请求需要在处理器处理之前进行一个拦截检??
 * 
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

	/**
	 * 用来检查session域对象中是否存有admin数据，若有放行，否则拦截
	 */
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		//取出session域对象中的admin数据
		HttpSession session = request.getSession();
		Admin admin = (Admin)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
		
		//对admin进行判断
		if (admin == null) {
			throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);			
		}
		
		//admin不为null，放??
		return true; 
	}

}
