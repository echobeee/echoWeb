package cn.echo.web.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.echo.web.pojo.Website;
import cn.echo.web.service.FavoriteService;


@Controller
@RequestMapping("/favorites")
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;
	
	private static final int PageSize = 10;
	
	/**
	 * 
	 * @param userId
	 * @param webSiteName
	 * @param websiteUrl
	 * @return 返回三种字符串：success（创建成功），existed（别名已存在），fail（创建失败）
	 */
	@RequestMapping("/addWebsite.do")
	@ResponseBody
	public String addWebsite(@RequestParam(required=true)String userId, 
			@RequestParam(required=true) String webSiteName,
			@RequestParam(required=true) String webSiteUrl) {
		/*
		 * 判断是否为空
		 */
		if(webSiteName.trim().equals("") || webSiteUrl.trim().equals("")) {
			return "fail";
		}
		return favoriteService.addWebsite(userId, webSiteName, webSiteUrl);

	}
	
	/**
	 * 查询用户的所有收藏夹
	 * 分页
	 * @param userId
	 * @param page  当前页
	 * @return
	 * 		返回一个Map集合的json
	 */
	@RequestMapping("/myWebsites.do")
	@ResponseBody
	public Map<String, Object> findUserWebsites(String userId,
					@RequestParam(required=false, defaultValue="1")int page) {

		return favoriteService.findWebsitesByUserId(userId.toLowerCase(), page, PageSize);
	}
	
	/**
	 * 自动补全
	 * @param userId
	 * @param toComplete
	 * @return
	 * 		返回字符串列表的json
	 */
	@RequestMapping("autoComplete.do")
	@ResponseBody
	public List<String> autoComplete(String userId, String toComplete) {
		return favoriteService.autoComplete(userId, toComplete);
	}
	
	/**
	 * 根据用户的输入内容查询收藏夹
	 * @param userId
	 * @param content
	 * @return
	 * 		返回Website对象列表的json
	 */
	@RequestMapping("/search.do")
	@ResponseBody
	public List<Website> findByContent(String userId, String content) {
		return favoriteService.findWebsitesByContent(userId, content);
	}
	
	@RequestMapping("delete.do")
	@ResponseBody
	public String deleteById(@RequestParam(required=true)String id) {
		String result = favoriteService.deleteWebsite(id);
		return result;
	}
	
	/**
	 *    更新网站（别名或者网址）
	 * @notice 注意这里传进来的不可能是相同的别名，由前端进行判断，若为相同则不发送请求 
	 * @param website
	 * @return
	 *   	 OK 更新成功
	 *       existed 已经有该别名
	 *         
	 */
	@RequestMapping("/update.do")
	@ResponseBody
	public String updateById(Website website) {
		String result = favoriteService.updateWebsite(website);
		return result;
	}
	
	
}
