package cn.echo.web.service;

import cn.echo.web.pojo.User;

public interface EmailService {
	
	/**
	 * 
	 * @param user
	 * @param method 发送的邮件的目的
	 * 1. active -> 帐号激活
	 * 2. reset -> 密码重设
	 * 3. memo,edittime,content -> 备忘录发送
	 */
	void sendEmail(User user, String... args);
}
