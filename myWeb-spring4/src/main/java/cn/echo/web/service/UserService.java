package cn.echo.web.service;

import cn.echo.web.pojo.User;

public interface UserService extends BaseService<User> {

	
	/**
     * 验证邮箱是否存在，如果邮箱存在了那么不给予注册
     * @param userEmail
     * @return
     */
    User validateEmailExist(String userEmail);


    /**
     * 验证用户是否存在,被激活了的邮箱才算是真正的用户
     * @param userEmail
     * @return
     */
    User validateUserExist(String userEmail);
    
    /**
     * 对密码进行md5加密
     * @param password
     */
    User encryptPassword(User user);
    
    /**
     * 修改密码时，确认新旧密码是否相同
     * @param newPassword
     * @param userId
     * @return
     */
    boolean checkPasswordTheSame(String newPassword, User user);
}
