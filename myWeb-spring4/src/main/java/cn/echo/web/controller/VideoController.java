package cn.echo.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.echo.web.douyinSpider.DouyinCrawl;
import cn.echo.web.douyinSpider.VideoService;
import cn.echo.web.pojo.VideoInfo;

@Controller
@RequestMapping("/douyin")
public class VideoController {

	@Autowired
	VideoService service;
	
	@Autowired
	DouyinCrawl crawler;
	
	Map<String, VideoInfo> map;
	
	// 爬虫数据存取文件
	String savepath ;
	
	Date date;
	
	
	@RequestMapping("/videos")
	public String getVideos(HttpServletRequest request) {
		date = new Date();
		savepath = request.getServletContext().getRealPath("/") + "WEB-INF/ftl/douyin/videos.xml";
		Map<String, VideoInfo> videos = service.getVideoMap(savepath); 
		request.setAttribute("videos", videos);
		map = videos;
		return "/douyin/videos";
	}
	
	/**
	 * @dest 通过后台来获取视频数据，因为浏览器无法修改user-agent，否则服务器会报403（forbidden）
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "/getVideo/{url}", method = RequestMethod.GET)
	public void getVideoStream(@PathVariable("url")String id, HttpServletResponse response) {
		VideoInfo vInfo = null;
		if(map != null && (vInfo = map.get(id)) != null) {
			
			// 状态码206就是有问题的。。。此处待解决
//			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("video/mp4");
			try {
				
				crawler.getResponse(vInfo.getVideoUrl(), response.getOutputStream(), vInfo.getVideoUrlBak());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 获取下拉加载的视频
	 * @return
	 */
	@RequestMapping("/getBatchVideos")
	@ResponseBody
	public List<VideoInfo> getBatchVideos() {	
		Date now = new Date();
		// ignore duplicate request within 3 seconds
		if(now.getTime() < date.getTime() + 3 * 1000) {
			return null;
		}
		
		date = now;
		map = service.getVideoMap(savepath);
		List<VideoInfo> list = new ArrayList<>();
		for(String key : map.keySet()) {
			list.add(map.get(key));
		}
		return list;
	}
}
