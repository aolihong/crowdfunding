package com.aolhon.crowd.mvc.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.aolhon.crowd.constant.CrowdConstant;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月9日 上午11:25:54
 * 配置类，不是完全的替代xml配置文件，仍然需要xml配置文件对本包进行组件扫描
 */
// 设置为配置类
@Configuration
// 启动Web环境下权限控制功能
@EnableWebSecurity
// 启用全局方法权限控制功能，并且设置prePostEnabled = true。
// 保证@PreAuthority、@PostAuthority、@PreFilter、@PostFilter生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CrowdUserDetailService userDetailsService;
	
	// 进行盐值加密认证
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		// 注释掉默认的配置
//		super.configure(auth);  
		
		// （1）使用内存认证登陆***********************************
//		builder
//			.inMemoryAuthentication()
//			.withUser("aolhon")
//			.password("123")
//			.roles("ADMIN");
		
		// （2）使用数据库认证登陆***********************************
		builder
			.userDetailsService(userDetailsService)
			.passwordEncoder(getBCryptPasswordEncoder());		// 进行盐值加密认证
	}

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		// 注释掉默认的配置
//		super.configure(http);
		
		security
			.authorizeRequests()		// 对请求进行授权
			.antMatchers("/admin/to/login/page.html")
			.permitAll()
			.antMatchers("/bootstrap/**")	// 针对静态资源进行设置，无条件访问
			.permitAll()                    // 针对静态资源进行设置，无条件访问
			.antMatchers("/crowd/**")       // 针对静态资源进行设置，无条件访问
			.permitAll()                    // 针对静态资源进行设置，无条件访问
			.antMatchers("/css/**")         // 针对静态资源进行设置，无条件访问
			.permitAll()                    // 针对静态资源进行设置，无条件访问
			.antMatchers("/fonts/**")       // 针对静态资源进行设置，无条件访问
			.permitAll()                    // 针对静态资源进行设置，无条件访问
			.antMatchers("/img/**")         // 针对静态资源进行设置，无条件访问
			.permitAll()                    // 针对静态资源进行设置，无条件访问
			.antMatchers("/jquery/**")      // 针对静态资源进行设置，无条件访问
			.permitAll()                    // 针对静态资源进行设置，无条件访问
			.antMatchers("/layer/**")       // 针对静态资源进行设置，无条件访问
			.permitAll()                    // 针对静态资源进行设置，无条件访问
			.antMatchers("/script/**")      // 针对静态资源进行设置，无条件访问
			.permitAll()                    // 针对静态资源进行设置，无条件访问
			.antMatchers("/ztree/**")       // 针对静态资源进行设置，无条件访问
			.permitAll()
//			.antMatchers("/bootstrap/**", "/css/**", "/fonts/**", 
//						 "/img/**", "/jquery/**", "/layer/**", 
//						 "/myjs/**", "/script/**", "/ztree/**")
//			.permitAll()
			// 增加角色权限设置
			// （1）方式一：在配置类的configure重写方法中调用
			//  antMatchers("访问资源")方法
			//  hasRole()
			//  hasAuthority()
			//  设置访问指定资源前需先获得相应角色权限
			//  (2)方式二：在处理器方法上添加注解进行添加角色权限
			//  也先需要在配置类上加上注解启用全局方法权限控制功能
//			.antMatchers("/admin/get/page.html")		// 设定访问控制。这里也可以请求的地址，可以不是一个真正文件的地址
//			.hasRole("经理")				// 要求具备”经理“角色
//			.access("hasRole('经理') OR hasAuthority('user:get')")	// 要求具备“经理 ”角色和“user:get”权限二者之一
			.anyRequest()				// 其他任意请求
			.authenticated()			// 认证后访问
			// 设置登陆
			.and()
			.csrf()					   // 防跨站请求伪造功能
			.disable()				   // 禁用csrf
			.formLogin()			   // 开启表单登陆功能
			.loginPage("/admin/to/login/page.html")
			.loginProcessingUrl("/security/do/login.html")
			.defaultSuccessUrl("/admin/to/main/page.html")
			.usernameParameter("loginAcct")
			.passwordParameter("userPswd")
			// 设置退出
			.and()
			.logout()
			.logoutUrl("/security/do/logout.html")
			.logoutSuccessUrl("/admin/to/login/page.html")
			// 设置异常处理
			.and()
			.exceptionHandling()
			.accessDeniedHandler(new AccessDeniedHandler() {
				
				@Override
				public void handle(
						HttpServletRequest request, 
						HttpServletResponse response,
						AccessDeniedException exception)
						throws IOException, ServletException {
					request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
					
					// 请求转发错误页面
					request.getRequestDispatcher("/WEB-INF/system-error1.jsp").forward(request, response);
				}
			});
		
	}
	
}
