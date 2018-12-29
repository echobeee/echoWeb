package cn.echo.web.pojo;
 
public class Website {
	String id;
	String userId;
	String webSiteName;
	public String getId() {
		return id;
	}
	/*
	 * （偷笑）改写了它的返回值嘻嘻
	 */
	public Website setId(String id) {
		this.id = id;
		return this;
	}
	String webSiteUrl;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWebSiteName() {
		return webSiteName;
	}
	public void setWebSiteName(String webSiteName) {
		this.webSiteName = webSiteName;
	}
	public String getWebSiteUrl() {
		return webSiteUrl;
	}
	public void setWebSiteUrl(String webSiteUrl) {
		this.webSiteUrl = webSiteUrl;
	}
	@Override
	public String toString() {
		return "Website [id=" + id + ", userId=" + userId + ", webSiteName=" + webSiteName + ", webSiteUrl="
				+ webSiteUrl + "]";
	}
	
}
