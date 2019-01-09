package cn.echo.web.douyinSpider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;



public class Signature {
	// 请求头
	public static final String header = "Aweme 3.4.0 rv:34008 (iPhone; iOS 12.0; zh_CN) Cronet";
	
	// 获取加密数据的api
	private final String API = "https://api.appsign.vip:2688";
	
	// 爬虫抖音链接1
	private final String douyin = "https://aweme.snssdk.com/aweme/v1/feed/?";
	
	// 爬虫抖音链接2
	private final String douyin2 = "https://aweme-eagle.snssdk.com/aweme/v1/feed/?";
	
	URL url;
	
	/*
	 * dest：get请求的连接，返回JSON数据
	 * mainly对getToken和getDevic使用
	 */
	public String goConnect(String Api) {
		String result = "";
		InputStream reader = null;
		try {
			url = new URL(API + Api);
			URLConnection connection = url.openConnection();
			connection.connect();
			reader =  connection.getInputStream();
			byte[] b = new byte[20 * 1024];
				if(reader.read(b) == -1) {
					System.err.println("没有数据哦");
					return "";
				}
				result = new String(b).trim();
		} catch (Exception e) {
			System.err.println("服务器应该出错啦....请看详情↓");
			e.printStackTrace();
			return result;
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
//	获取token
//	有效期60min
	public String getToken() {	
		String tokenAll = goConnect("/token/douyin");
		return tokenAll.length() != 0 ? tokenAll.substring(28,60) : tokenAll;
	}
	
	/**
	 * dest：返回设备信息
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public Object getDevice() {
		String string = goConnect("/douyin/device/new");
		Map<String, Object> map2 = null;
		if(string.length() != 0) {
			int i = string.indexOf("iid");
			int j = string.indexOf("device_id");
			Map<String,Object> map = new Gson().fromJson(string, Map.class);
			map2 =(Map<String, Object>) map.get("data");
			map2.put("iid", string.substring(i+6,i+17));
			map2.put("device_id", string.substring(j+12, j+23));
		} else {
			map2 = new HashMap<String, Object>();
			// 如果服务器崩了，就使用默认的设备信息
			map2.put("device_id", "53899138286");
			map2.put("os_api", "18");
			map2.put("screen_width", "750");
			map2.put("vid", "3F962948-ED65-4C83-B99D-E6B271CF4363");
			map2.put("iid", "52998149013");
			map2.put("idfa", "D3A9F3DB-62F1-41DD-A325-145871F3BCE3");
			map2.put("device_type", "iPhone7,2");
			map2.put("device_platform", "iphone");
			map2.put("openudid", "c84838f8ce347572b5fb48f0eb8bb827846af6fc");
			map2.put("device_id", "53899138286");
			
		}
		
		return map2;
	}
	
	/**
	 * dest: 返回加密参数
	 * @param token
	 * @param params
	 * @return 返回一个Map
	 */
	public Object getParams(String token, String params) {
		Map<String, String> post = new HashMap<>();
		post.put("token", token);
		post.put("query", params);		
		Map<String, Object> map = new Gson().fromJson( HttpClientUtils.doJsonPost(API + "/sign", post, "utf-8"), Map.class);
		return map.get("data");
	}
	
	/**
	 * 爬虫主函数，将爬取数据写入到文件中
	 * @param params url参数
	 * @param pathname 写入文件的路径名
	 * @return 文件路径
	 */
	public String connect(String params, String pathname) {
//		File file = new File("E:/json.xml");
		File file = new File(pathname);
		FileOutputStream fos = null;
		
		try {
			url = new URL(douyin + params);

			fos = new FileOutputStream(file);
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("User-Agent", header);
			connection.connect();
			BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
			byte[] b = new byte[20 * 1024];
			int len;
			while((len = bis.read(b))!= -1) {
				fos.write(b, 0, len);
			}

			bis.close();
			fos.close();
		} catch (MalformedURLException e) {
			System.err.println("URL出错！");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return pathname;
	}
}
