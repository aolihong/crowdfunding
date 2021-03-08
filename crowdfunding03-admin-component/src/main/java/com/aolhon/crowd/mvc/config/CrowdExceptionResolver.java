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
 * @time 2021??1??30?? ��???11:52:32
 * 
 * ��Ϊ����ע����쳣�����ࡣ����˳�����ִ������оٵ��쳣�󣬾ͻᵽ�����еķ�������ִ?? �쳣�������???
 *       1.����xml�ļ�����
 *       2.����ע�ⷽʽ
 */

// @ControllerAdvice������ǰ����Ϊ����ע���쳣������
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
	 * ����Լ���ֶ�LoginAcct���쳣
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
	
	//������������
	//����ע�ⷽʽ��AccessForbiddenException�쳣��������Ч��ʹ�û���xml�ļ����õķ�ʽ�Ż���???
	
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
	//�׳���½ʧ���쳣���쳣����???
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
	// @ExceptionHandler�����˷�����Ϊ�쳣����ľ��巽��(value ��??????? ��ָ????����ʲô���͵��쳣�����Զ�����??)
	@ExceptionHandler(value = ArithmeticException.class)
	public ModelAndView resolveArithmeticException(HttpServletRequest request, HttpServletResponse response,
			ArithmeticException exception) throws IOException {
		/*
		 * ��Ҫ?????��ǰ�������Ƿ�??? ��???��??? ��??? ajax����??? ��Ϊ��������ķ���???��ͬ???? ��???���󷵻ص���ModelAndView��???
		 * ajax���󷵻صĿ�����null
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

	
	
	/*******************************����???************************************/
	
	// ��Ϊ??�����߷�������Ϊ���涼���ظ��Ĵ��룬ֻ��???????��ʶͼ�����ı�Ϳ�???
	private ModelAndView commonResolve(HttpServletRequest request, HttpServletResponse response, Exception exception,
			String viewName) throws IOException {

		// 1.�ж�������
		boolean judgeResult = CrowdUtil.judgeRequestType(request);

		// 2.Ϊ�� ��ajax����
		if (judgeResult) {

			// 3.����ResultEntity����
			ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());

			// 4.����gson����
			Gson gson = new Gson();

			// 5.��resultEntity��������ת����JSON�ַ�����
			String json = gson.toJson(resultEntity);

			// 6.��JSON�ַ�����Ϊ��Ӧ�巵�ظ������
			response.getWriter().write(json);

			// 7.���������Ѿ�ͨ��ԭ����response���󷵻�����Ӧ���Բ��ṩModelAndView����
			return null;

		}

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);

		modelAndView.setViewName(viewName);

		return modelAndView;
	}
}
