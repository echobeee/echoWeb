package cn.echo.web.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.echo.web.pojo.ActiveUser;
import cn.echo.web.pojo.User;
import cn.echo.web.service.UserService;

/**
 * 自定义token
 * @author echo
 *
 */
public class CustomRealm extends AuthorizingRealm {

	@Autowired
	UserService userService;
	
//	支持usernamepassword token
	@Override
	public boolean supports(AuthenticationToken token) {
		// TODO Auto-generated method stub
		return token instanceof UsernamePasswordToken;
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//用户帐号
		String username = (String) token.getPrincipal();
		System.out.println(token.getCredentials());
		// 根据用户帐号从数据库取出盐和加密后的值
		// 如果根据账号没有找到用户信息则返回null，shiro抛出异常"账号不存在"
		User user = userService.selectByPrimaryKey(username);
		if(user == null) {
			throw new UnknownAccountException("帐号不存在");
		} else if(user.getActiState().equals(0)) {
			throw new UnknownAccountException("用户未激活！请重新注册！");
		}
		String password = user.getUserPassword();
		String salt = user.getSalt();
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserId(username);
		activeUser.setUserNickname(user.getUserNickname());
		activeUser.setUserEmail(user.getUserEmail());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(activeUser, password, ByteSource.Util.bytes(salt), this.getName());
		return authenticationInfo;
	}

}
