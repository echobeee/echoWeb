package cn.echo.web.service;

import cn.echo.web.pojo.User;

public interface EmailService {
	
	/**
	 * 
	 * @param user
	 * @param method 发送的邮件的目的
	 */
	void sendEmail(User user, String... args);
}
