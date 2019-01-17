package cn.echo.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.echo.utils.vcode.Captcha;
import cn.echo.utils.vcode.GifCaptcha;
import cn.echo.web.exception.UserException;
import cn.echo.web.pojo.ActiveUser;
import cn.echo.web.pojo.User;
import cn.echo.web.service.EmailService;
import cn.echo.web.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	UserService userService;

	@Autowired
	EmailService emailService;
	
	// id不存在
	private static final String NO_SUCH_ID = "noUser";
	
	// 链接错误
	private static final String CANT_FIND_404 = "incorrect";
	
	// 链接失效
	private static final String LINK_EXPIRED = "disabled";
	
	public String profileEdit(User user) {
		
		return null;
	}
	
	/**
	 * 用户进入的主页，在session中设置user
	 * 已经在filter中设置，所以其实没什么用
	 * @param session
	 * @return
	 */
	@RequestMapping("/welcome.do")
	public String welcome(HttpSession session) {

		Subject subject = SecurityUtils.getSubject();
		ActiveUser user = null;
		if(subject.getPrincipals().iterator().hasNext()) {
			user = (ActiveUser) subject.getPrincipals().iterator().next();
		}
		session.setAttribute("user", user);
		return "welcome";
	}

	/**
	 * 用户注册
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("/register.do")
	public String register(@Validated User user, BindingResult bindingResult) {
		List<ObjectError> errors = bindingResult.getAllErrors();
		if (errors != null && !errors.isEmpty()) {
			for (ObjectError error : errors) {
				System.out.println(error);
			}
			// 若出现校验错误，跳转到注册页面
			return "/user/register";
		}
		// 没有出现错误，则对密码进行加密
		User encryptdUser = userService.encryptPassword(user);

		// 发送邮件
		emailService.sendEmail(encryptdUser, "active");

		// 插入数据库
		userService.insert(encryptdUser);

		// 成功注册，转发到登录页面
		return "user/login";
	}

	/**
	 * 注册确认链接
	 * 
	 * @param active
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/activate")
	public String activateConfirm(String active, String id, HttpServletRequest request) throws Exception {
		User user = userService.selectByPrimaryKey(id);
		// id不存在
		if (user == null) {
			throw new UserException(NO_SUCH_ID);
		}
		// 已激活
		if (user.getActiState().equals(1)) {
			throw new UserException("acitvated");
		}
		if (user.getActiCode().equals(active)) {
			long now = new Date().getTime();
			int diff = (int) ((now - user.getTokenExptime().getTime()) / (1000 * 60 * 60));
			// 时间差为24小时内
			// 激活成功
			if (diff < 24) {
				user.setActiState(1);
				userService.updateByPrimaryKey(user);
				request.setAttribute("message", "activated");
				return "user/mail";
			} else {
				// 超时则重新注册
				userService.deleteByPrimaryKey(id);
				throw new UserException(LINK_EXPIRED);
			}
		} else { // 激活码不正确
			throw new UserException(CANT_FIND_404);
		}
	}

	/**
	 * 页面发送AJAX来确认账户是否已存在，已存在则不能注册
	 * 
	 * @return
	 */
	@RequestMapping("/checkReg")
	@ResponseBody
	public String ajaxCheck(
			@RequestParam(required = false, defaultValue = "1") String userId,
			@RequestParam(required = false, defaultValue = "1") String userEmail) {
		User user = null;
			user = userService.validateEmailExist(userEmail);
			/**
			 * 若未激活，则重新注册
			 */
			if (user != null && user.getActiState().equals(1)) {
				return "email";
			}

			user = userService.selectByPrimaryKey(userId);
			if (user != null && user.getActiState().equals(1)) {
				return "UserID";
			}
			if(user != null) {
				userService.deleteByPrimaryKey(user.getUserId());
			}	
		return "Registable";
	}

	@RequestMapping("toLogin.do")
	public String toLogin(HttpSession session) {
		// 如果已经登陆
		if(session.getAttribute("user") != null) {
			return "welcome";
		}
		return "user/login";
	}
	
	/**
	 * 登录请求 已在shiro中用authc来处理 主要是捕获关于帐号登陆的异常
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request, PrintWriter writer) throws Exception {
		// 如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
		String exceptionClassName = (String) request
				.getAttribute("shiroLoginFailure");
		System.out.println(exceptionClassName);
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(
					exceptionClassName)) {
//				throw new UserException("noExist");
				writer.write("noExist");
				return null;
			} else if (IncorrectCredentialsException.class.getName().equals(
					exceptionClassName)) {
//				throw new UserException("password");
				writer.write("password");
				return null;
			} else if ("randomCodeError".equals(exceptionClassName)) {
//				throw new UserException("captcha");
				writer.write("captcha");
				return null;
			} else {
				throw new Exception();// 最终在异常处理器生成未知错误
			}
		}
		
		
		writer.write("succuss");	
		
		// 此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
		// 登陆失败还到login页面
		return null;
	}

	/**
	 * 生成动态验证码
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/getValidCode", method = RequestMethod.GET)
	public void getValidateCode(HttpServletResponse response,
			HttpServletRequest request, HttpSession session) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/gif");

		// 生成gif验证码图的长、宽、字符数
		Captcha captcha = new GifCaptcha(164, 42, 4);
		// 输出到响应中
		captcha.out(response.getOutputStream());
		// 在session中设置正确的验证码
		session.setAttribute("captcha", captcha.text().toLowerCase());

	}
	
	

	/**
	 * 忘记密码，则发送邮件到用户邮箱
	 * 到指定网址进行重设密码
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping("/forgetPassword.do")
	@ResponseBody
	public String forgetPassowrd(String userId ) {
		
		User user = userService.selectByPrimaryKey(userId);
		if(user == null) {
			// 用户不存在,返回到忘记密码页面
			return NO_SUCH_ID;
		}
		/*
		 * 发送重设密码的邮件到用户邮箱进行密码重新设置
		 */
		emailService.sendEmail(user, "reset");
		
		// 跳转到发送邮件成功页面
		return "succuss";
		
	}
	
	/**
	 * 重置密码的确认邮件页面
	 * @param session
	 * @param reset
	 * @param userId
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/reset")
	public String resetPassword(HttpSession session, String reset, String id) throws Exception {
		
		User user = userService.selectByPrimaryKey(id);
		if(user == null) {
			// 用户找不到，返回404
			throw new UserException(NO_SUCH_ID);
		}
		
		// 比较是否重置码正确
		String correct = new Md5Hash(user.getUserPassword()).toString();
		if(!correct.equalsIgnoreCase(reset)){
			throw new UserException(CANT_FIND_404);
		}
		
		session.setAttribute("userId", id);
		
		// 跳转到重置密码的页面
		return "redirect:/go/user/reset";
	}
	
	/**
	 * 修改密码
	 * @param userId
	 * @param newPassword
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/newPassword.do")
	public String resetSubmit(String userId, String newPassword) throws Exception {
		User user = userService.selectByPrimaryKey(userId);
		if(user == null){
			// 用户找不到，返回404
			throw new UserException(NO_SUCH_ID);
		}
		user.setUserPassword(newPassword);
		User encrpytedUser = userService.encryptPassword(user);
		// 更新数据库中的记录
		userService.updateByPrimaryKey(encrpytedUser);
		// 修改密码成功，跳转到成功页面
		return "redirect:go/user/resetSuccess";
	}
	
	@RequestMapping("/checkPassword")
	@ResponseBody
	public Map<String, Boolean> checkPassword(String newPassword, HttpSession session) throws Exception {
		User user = userService.selectByPrimaryKey((String) session.getAttribute("userId"));
		
		if(user == null) {
			throw new UserException(NO_SUCH_ID);
		}
		Map<String, Boolean> map = new HashMap<>();
		map.put("valid", !userService.checkPasswordTheSame(newPassword, user));
		return map;
	}
	
}
