package cn.echo.web.pojo;

import java.io.Serializable;


/**
 * 激活用户
 * 为了实现rememberMe，要实现java.io.Serializable接口
 * @author bee
 *
 */
public class ActiveUser implements Serializable{
	
	 	private String userId;
	    private String userNickname;
	    private String userEmail;
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getUserNickname() {
			return userNickname;
		}
		public void setUserNickname(String userNickname) {
			this.userNickname = userNickname;
		}
		@Override
		public String toString() {
			return "ActiveUser [userId=" + userId + ", userNickname=" + userNickname + ", userEmail=" + userEmail + "]";
		}
		public String getUserEmail() {
			return userEmail;
		}
		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}
	    
	    
}
