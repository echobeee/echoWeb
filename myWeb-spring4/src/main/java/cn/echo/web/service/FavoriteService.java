package cn.echo.web.service;

import java.util.List;
import java.util.Map;

import cn.echo.web.pojo.Website;

public interface FavoriteService {
	/**
	 * 
	 * @param userId
	 * @param webSiteName
	 * @param websiteUrl
	 * @return 
	 * 		返回三种字符串：success（创建成功），existed（别名已存在），fail（创建失败）
	 */
	String addWebsite(String userId, String webSiteName, String websiteUrl);
	/**
	 * 自动补全
	 * @param userId
	 * @param toComplete
	 * @return
	 */
	List<String> autoComplete(String userId, String toComplete);
	/**
	 * 根据用户id查找其网址
	 * 【分页】
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> findWebsitesByUserId(String userId, Integer page, Integer pageSize);
	/**
	 * 通过内容查找网站【进行高亮】
	 * @param userId
	 * @param content
	 * @return
	 */
	List<Website> findWebsitesByContent(String userId, String content);
	/**
	 * 通过网址id删除网址
	 * @param deleteId
	 * @return
	 *  	返回success（删除成功） fail（删除失败）
	 */
	String deleteWebsite(String deleteId);
	
	/**
	 * 更新网址
	 * @param website
	 * @return
	 * 		返回OK（更新成功），返回existed（已存在该别名），否则为失败
	 */
	String updateWebsite(Website website);
	
}
