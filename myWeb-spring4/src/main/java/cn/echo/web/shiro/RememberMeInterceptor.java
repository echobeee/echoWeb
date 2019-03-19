package cn.echo.web.shiro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.echo.web.pojo.ActiveUser;
import cn.echo.web.pojo.User;
import cn.echo.web.service.UserService;

/**
 * 拦截rememberMe的请求，添加user到session中
 * @author echo
 *
 */
public class RememberMeInterceptor implements HandlerInterceptor {

	 private final Logger logger = Logger.getLogger(RememberMeInterceptor.class);
	
	@Autowired
	private UserService userService;
	
	public RememberMeInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Subject user = SecurityUtils.getSubject();
		
		// 判断是不是通过记住我登录
		if( !user.isAuthenticated() && user.isRemembered()) {
			
			logger.debug("Tick remembered me before, put user [" + ((ActiveUser)user.getPrincipal()).getUserId() + "] in session");
			
			user.getSession().setAttribute("user", user.getPrincipal());
			
		}
		
		// execution chain keep dealing with request & response 
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
