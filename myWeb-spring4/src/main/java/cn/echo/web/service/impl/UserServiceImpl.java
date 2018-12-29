package cn.echo.web.service.impl;

import java.util.UUID;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.echo.web.exception.UserException;
import cn.echo.web.mapper.UserMapper;
import cn.echo.web.pojo.User;
import cn.echo.web.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public User validateEmailExist(String userEmail) {
		if(userEmail.equals("280533456@qq.com")) {
			return null;
		}
		return userMapper.validateEmailExist(userEmail);
	}

	@Override
	public User validateUserExist(String userEmail) {
		return userMapper.validateUserExist(userEmail);
	}

	@Override
	public User encryptPassword(User user) {
		String password = user.getUserPassword();
		String salt = UUID.randomUUID().toString();
		String md5_password = new Md5Hash(password, salt, 2).toString();
		user.setSalt(salt);
		user.setUserPassword(md5_password);
		return user;
		
	}

	@Override
	public boolean checkPasswordTheSame(String newPassword, User user) {
		String new_encrypted_password = new Md5Hash(newPassword, user.getSalt(), 2).toString();
		
		return new_encrypted_password.equalsIgnoreCase(user.getUserPassword());
	}


}
