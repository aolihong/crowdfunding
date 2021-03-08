package com.aolhon.crowd.mvc.config;

import java.io.IOException;

import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.aolhon.crowd.constant.CrowdConstant;
import com.aolhon.crowd.exception.AccessForbiddenException;
import com.aolhon.crowd.exception.LoginAcctAlreadyInUseException;
import com.aolhon.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.aolhon.crowd.exception.LoginFailedException;
import com.aolhon.crowd.util.CrowdUtil;
import com.aolhon.crowd.util.ResultEntity;
import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.google.gson.Gson;

/**
 * @author aolihong
 * @email aolhon@163.com
 * @time 2021??1??30?? 上???11:52:32
 * 
 * 作为基于注解的异常处理类。当后端程序出现此类中列举的异常后，就会到此类中的方法进行执?? 异常处理可以???
 *       1.基于xml文件配置
 *       2.基于注解方式
 */

// @ControllerAdvice：将当前类作为基于注解异常处理类
@ControllerAdvice
public class CrowdExceptionResolver {
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView resolveException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception exception) throws IOException{
		
		String viewName = "system-error1";
		return commonResolve(request, response, exception, viewName);
	}
	
	@ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
	public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException(
			HttpServletRequest request,
			HttpServletResponse response,
			LoginAcctAlreadyInUseForUpdateException exception) throws IOException{
		
		String viewName = "system-error";
		return commonResolve(request, response, exception, viewName);
	}
	
	/**
	 * 处理约束字段LoginAcct的异常
	 * @param request
	 * @param response
	 * @param exception
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
	public ModelAndView resolveLoginAcctAlreadyInUseException(
			HttpServletRequest request,
			HttpServletResponse response,
			LoginAcctAlreadyInUseException exception) throws IOException{
		
		String viewName = "admin-add";
		return commonResolve(request, response, exception, viewName);
	}
	
	//？？？？？？
	//基于注解方式的AccessForbiddenException异常处理不会生效，使用基于xml文件配置的方式才会生???
	
//	/**
//	 * 
//	 * @param request
//	 * @param response
//	 * @param exception
//	 * @return
//	 * @throws IOException
//	 */
//	@ExceptionHandler(value = AccessForbiddenException.class)
//	public ModelAndView resolveAccessForbiddenException(
//			HttpServletRequest request,
//			HttpServletResponse response,
//			AccessForbiddenException exception) throws IOException{
//		
//		String viewName = "admin-login";
//		
//		return commonResolve(request, response, exception, viewName); 
//		
//	}


	/**
	 * 
	 * @param request
	 * @param response
	 * @param exception
	 * @return
	 * @throws IOException
	 */
	//抛出登陆失败异常的异常处理方???
	@ExceptionHandler(value = LoginFailedException.class)
	public ModelAndView resolveLoginFailedException(
			HttpServletRequest request,
			HttpServletResponse response,
			LoginFailedException exception) throws IOException{
		String viewName = "admin-login";
		return commonResolve(request, response, exception, viewName);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param exception
	 * @return
	 * @throws IOException
	 */
	// @ExceptionHandler：将此方法作为异常处理的具体方法(value 属??????? 是指????处理什么类型的异常，可以多种异??)
	@ExceptionHandler(value = ArithmeticException.class)
	public ModelAndView resolveArithmeticException(HttpServletRequest request, HttpServletResponse response,
			ArithmeticException exception) throws IOException {
		/*
		 * 先要?????当前的请求是否??? 普???请??? 还??? ajax请求??? 因为两种请求的返回???不同???? 普???请求返回的是ModelAndView对???
		 * ajax请求返回的可以是null
		 */

		String viewName = "system-error";

		return commonResolve(request, response, exception, viewName);

	}

//	/**
//	 * 
//	 * @param request
//	 * @param response
//	 * @param exception
//	 * @return
//	 * @throws IOException
//	 */
//	@ExceptionHandler(value = NullPointerException.class)
//	public ModelAndView resolveNullPointerException(
//			HttpServletRequest request, 
//			HttpServletResponse response,
//			NullPointerException exception) throws IOException {
//
//		String viewName = "system-error";
//
//		return commonResolve(request, response, exception, viewName);
//
//	}

	
	
	/*******************************工具???************************************/
	
	// 作为??个工具方法，因为里面都是重复的代码，只需???????辑识图名做改变就可???
	private ModelAndView commonResolve(HttpServletRequest request, HttpServletResponse response, Exception exception,
			String viewName) throws IOException {

		// 1.判断请求类
		boolean judgeResult = CrowdUtil.judgeRequestType(request);

		// 2.为真 是ajax请求
		if (judgeResult) {

			// 3.创建ResultEntity对象
			ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());

			// 4.创建gson对象
			Gson gson = new Gson();

			// 5.将resultEntity对象数据转换成JSON字符数据
			String json = gson.toJson(resultEntity);

			// 6.将JSON字符串作为响应体返回给浏览器
			response.getWriter().write(json);

			// 7.由于上面已经通过原生的response对象返回了响应，以不提供ModelAndView对象
			return null;

		}

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);

		modelAndView.setViewName(viewName);

		return modelAndView;
	}
}
