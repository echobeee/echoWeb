package cn.echo.web.douyinSpider;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.Trigger.TriggerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.math.DD;

import cn.echo.web.pojo.VideoInfo;
import cn.echo.web.quartz.ReconnectJob;
import cn.echo.web.test.newTest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@Service
public class DouyinCrawl {

	@Autowired
	@Qualifier("schedulerFactoryBean")
	Scheduler scheduler;

	private static Signature signature = new Signature();

	// 获取token - 60min有效期
	private static String token = "";

	private static Date token_expired_time = null;

	// 获取设备信息
	private static Object device = signature.getDevice();
	
	private static Logger  logger = Logger.getLogger(DouyinCrawl.class);
	
	/**
	 * 代替浏览器发送请求
	 * 添加header
	 **/
	public void getResponse(String url, OutputStream outstream, String backup) {
		CloseableHttpResponse response = null;
		try {
			
			CloseableHttpClient httpClient = org.apache.http.impl.client.HttpClients.createDefault();
	        RequestConfig requestConfig = RequestConfig.custom()
	                .setRedirectsEnabled(false)
	                .build();
	        HttpGet get = new HttpGet(url);
	        get.setConfig(requestConfig);
	        get.setHeader("Accept","*/*");
	        get.setHeader("User-Agent", Signature.header);
	        response = httpClient.execute(get);
	       
	        // 返回的状态码
	        int status = response.getStatusLine().getStatusCode();

	        if(status != 200) {
	        	System.err.println("请求响应错误，返回状态码为 " + status);
	        	getResponse(backup, outstream, null);
	        	return ;
	        }
	        
	        HttpEntity entity = response.getEntity();
 
	        // output to response's outputStream
	        entity.writeTo(outstream);
	        
	        System.out.println(entity.getContentType() + " output done!");
	        
		}  catch (UnknownHostException e) { // host错误
			if(backup != null) {
				getResponse(backup, outstream, null);
			} else {
				System.err.println("video 链接失效...");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			if(response != null) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * 爬取抖音视频数据，并将json存到savePath路径的文件中
	 * @param savepath
	 * @return
	 */
	public Map<String, VideoInfo> getVedioList(String savepath) {
		// 上一次服务器出错 or 第一次调用 or token过期
		if (token.length() == 0 || new Date().after(token_expired_time)) {
			token = signature.getToken();
			token_expired_time = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
			logger.debug("Last token is invalid, go get new token..");
		}

		if (token.length() == 0) { // 此次服务器出错
			logger.debug("Token still invalid, need to be checked next time");
			return null;
		}
		return extractStats(goAndCrawl(savepath));
	}

	/**
	 * dest:这里想完成的是，当服务器出错的时候，尝试每隔一段时间（4h）就去获取新的token（饿汉式）
	 * 但其实利用上面的判断，每次请求去获取就够了（懒汉式），因此该函数不需要实现 
	 * 实现方法：设定新的Trigger和Job，调用并从Job来修改这里的token
	 */
	@Deprecated
	private void tryConnectServer() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("crawl", this);
		
		JobDetail job = JobBuilder.newJob(ReconnectJob.class).withIdentity("1", "reconnect_group").usingJobData(new JobDataMap(map)).build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("1", "trigger_group")
				.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(4)).build();
		
		try {
			scheduler.scheduleJob(job, trigger);
			if(scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Deprecated
	public void stopConnecting() {
		try {
			scheduler.pauseTrigger(new TriggerKey("1", "trigger_group"));
		
			scheduler.unscheduleJob(new TriggerKey("1", "trigger_group"));
		
			scheduler.deleteJob(new JobKey("1", "reconnect_group"));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	extractStats(goAndCrawl("E:/json.xml"));

	/**
	 * dest：进行爬虫并存到savepath中的文件中
	 * 
	 * @param savepath
	 * @return 返回爬虫数据的文件路径
	 */
	private String goAndCrawl(String savepath) {
		return signature.connect(getConnectingUrl(), savepath);
	}

	/**
	 * continued: 进行多线程爬虫，爬取cursor，offset还未确定？ dest:对抖音的feed流进行爬取，存到文件中
	 * 
	 * @return 返回爬虫链接的各种参数
	 */
	private String getConnectingUrl() {
		// 参数类
		Params params = new Params();

		// 参数填补
		params = (Params) Utils.map2Object((Map<String, Object>) device, Params.class);
		params.intial();

		// 请求加密数据的参数
		String json = Utils.object2Url(params);

		// 返回的加密参数 ts,mas,as
		Map secret = (Map) signature.getParams(token, json);
		for (Object key : secret.keySet()) {
			if (key.equals("mas")) {
				// mas参数
				params.setMas((String) secret.get(key));
				// as参数
			} else if (key.equals("as")) {
				params.setAs((String) secret.get(key));
				// ts参数
			} else if (key.equals("ts")) {
				params.setTs((String) secret.get(key));

			}
		}

		// 抖音url - 将所有参数转换为url
		String getParams = Utils.object2Url(params);
		// signature.connect(getParams,savepath);
		System.out.println("url params: " + getParams);
		return getParams;
	}

	/**
	 * 将存在文件中的json数据extract得到需要的特定数据，如评论数，点赞数，转发数
	 * 
	 * @param filename
	 * @return 包含视频数据的VideoInfo
	 */
	private Map<String, VideoInfo> extractStats(String filename) {
		File file = new File(filename);

		// 转JSON时候的配置，防止循环
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		// 结果list
		Map<String, VideoInfo> map =  new HashMap<>();

		try {
			// 读取文件中的JSON数据，转换为字符串
			String content = FileUtils.readFileToString(file, "utf-8");

			// 将JSON字符串转化为JSONObject
			JSONObject json = JSONObject.fromObject(content, config);

			// JSON中的aweme_list转换为JSON对象 --- 就是【视频列表】
			JSONArray arrays = JSONArray.fromObject(json.get("aweme_list"), config);

			// 包含视频url的json
			JSONObject video_url = null, poster_url = null;

			// 每个视频的json（包含作者，音乐，视频..）
			JSONObject oo = null;

			// 播放链接，共4个
			JSONArray urls = null, posters = null;

			// 视频的数据，点赞数，评论数等等..
			JSONObject stats = null;
			

			for (Object object : arrays) {
				// 要收集的数据 都是从oo中获取
				oo = (JSONObject) object;

				// 抖音数据
//				System.out.println(oo.get("statistics"));
				stats = oo.getJSONObject("statistics");

				video_url = ((JSONObject) oo.get("video")).getJSONObject("download_addr");
				
				poster_url = ((JSONObject) oo.get("video")).getJSONObject("cover");
				
				// 播放列表
				urls = video_url.getJSONArray("url_list");
				
				posters = poster_url.getJSONArray("url_list");

				VideoInfo video = new VideoInfo();

				video = (VideoInfo) JSONObject.toBean(stats, VideoInfo.class);
				// 播放链接默认取第一条
				video.setVideoUrl(getVideoUrl(urls.getString(0)));
				
				// title
				video.setDesc(oo.getString("desc"));
				
				// 用户昵称
				video.setNickname(oo.getJSONObject("author").getString("nickname"));
				
				// 备用url
				video.setVideoUrlBak(getVideoUrl(urls.getString(2)));
				
				// poster
				video.setPoster(posters.getString(0));
				
				System.out.println(video);
				map.put(video.getAweme_id(), video);
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取真实的播放地址
	 * 
	 * @param fakeUrl
	 * @return
	 */
	private String getVideoUrl(String fakeUrl) {

		try {
			// 第一次链接，获取真实的播放视频的网址
			URL url = new URL(fakeUrl);
			URLConnection connection;
			connection = url.openConnection();
			connection.addRequestProperty("User-Agent", Signature.header);
			connection.connect();
			// 在response的Location Header中
			String loc = connection.getHeaderField("Location");
			return loc;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	

	public static void setToken(String token) {
		DouyinCrawl.token = token;
	}

	public static void setToken_expired_time(Date token_expired_time) {
		DouyinCrawl.token_expired_time = token_expired_time;
	}
	
}
