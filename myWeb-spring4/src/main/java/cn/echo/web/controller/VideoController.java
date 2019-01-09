package cn.echo.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.echo.web.douyinSpider.DouyinCrawl;
import cn.echo.web.pojo.VideoInfo;

@Controller
@RequestMapping("/douyin")
public class VideoController {

	@Autowired
	DouyinCrawl crawler;
	
	@RequestMapping("/videos")
	public String getVideos(HttpServletRequest request) {
		String savepath = request.getServletContext().getRealPath("/") + "WEB-INF/ftl/douyin/videos.xml";
		System.out.println(savepath);
		List<VideoInfo> videos = crawler.getVedioList(savepath);
		request.setAttribute("videos", videos);
		return "/douyin/videos";
	}

}
