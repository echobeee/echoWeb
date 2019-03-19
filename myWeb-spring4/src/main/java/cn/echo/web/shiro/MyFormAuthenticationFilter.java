package cn.echo.web.shiro;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import cn.echo.web.pojo.ActiveUser;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	
	private final Logger logger = Logger.getLogger(MyFormAuthenticationFilter.class);
	
	/*
	 * 返回跳转到登录前的页面
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		
		HttpServletRequest request2 = (HttpServletRequest) request;
		HttpServletResponse response2 = (HttpServletResponse) response;
		
		// 把user放在session中
		Session session =  subject.getSession();
		session.setAttribute("user", activeUser);
		logger.debug("Login user: " + activeUser);
		
		try {
			logger.debug("CallbackUrl : " + WebUtils.getSavedRequest(request).getRequestURI());
			
			 if (!"XMLHttpRequest".equalsIgnoreCase(
					 request2.getHeader("X-Requested-With"))) {
				 	// 不是ajax请求
				 	logger.debug("Not ajax login, keep going login ");
				 
		            issueSuccessRedirect(request, response); // 继续执行
		        } else { // ajax請求直接返回callbackUrl
		        	logger.debug("Ajax login, put callbackurl in response and return it");
		            response2.setCharacterEncoding("UTF-8");
		            PrintWriter out = response2.getWriter();
		            out.print(WebUtils.getSavedRequest(request).getRequestURI());
		            out.flush();
		            out.close();
		        }
		} catch(Exception exception) {
			exception.printStackTrace();
			
		}
		
	        return false;

	}
	/**
	 * 在验证账号密码前判断验证码是否正确
	 */
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {

		// 校验验证码
		// 从session获取正确的验证码
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		//页面输入的验证码
		String randomcode = request.getParameter("randomcode");
		
		//从session中取出验证码
		String validateCode = (String) session.getAttribute("captcha");
		if (randomcode != null && validateCode != null && !randomcode.equalsIgnoreCase(validateCode)) {
			// randomCodeError表示验证码错误 
			
			logger.debug("Input validatecode: [" + randomcode + "], correct validatecode: [" + validateCode + "], not match, login denied");
			
			request.setAttribute("shiroLoginFailure", "randomCodeError");
			//拒绝访问，不再校验账号和密码 
			return true;
		}
		
		logger.debug("Input validatecode: [" + randomcode + "], correct validatecode: [" + validateCode + "], matched!");
		
		// 验证码通过
		return super.onAccessDenied(request, response, mappedValue);
	}

}
