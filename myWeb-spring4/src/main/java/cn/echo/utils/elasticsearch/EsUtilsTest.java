package cn.echo.utils.elasticsearch;


import java.io.IOException;
import java.util.List;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mysql.fabric.xmlrpc.Client;

import cn.echo.web.pojo.Website;
import cn.echo.web.test.newTest;

public class EsUtilsTest {
	
	EsUtils esDao = new EsUtils();
	TransportClient client = null;
	
	@Before
	public void publ() {
		
		client = EsClientBuilder.getTransportClient();
	}
	
	@After
	public void pub2() {
		client.close();
	}

	@Test
	public void addTest() {
	 	XContentBuilder json;
		try {
			json = XContentFactory.jsonBuilder()
					.startObject()
					.field("userId", "bean")
					.field("webSiteName", "抖音APP的视频数据采集方法（简书首发）")
					.field("webSiteUrl", "https://cloud.tencent.com/developer/article/1131879")
					.endObject();
			System.out.println(esDao.addIndex(client, json));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	@Test
	public void checkExists() {
		
		System.out.println(esDao.checkIfExists(client, "bean", "抖音APP的视频数据采集方法（简书首发）"));
		System.out.println(esDao.checkIfExists(client, "bee", "百度"));
		System.out.println(esDao.checkIfExists(client, "bee", "百度知道"));
		System.out.println(esDao.checkIfExists(client, "bee", "CA分析"));

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		for(Website webs : (List<Website>) esDao.indexByUserId(client, "bean", 2, 105).get("websiteList")) {
			webs.setUserId("beanId");
			System.out.println(esDao.updateDocument(client, new Gson().toJson(webs), webs.getId()));
		}
	}
	
	@Test
	public void autoTest() {

		for(String ss : esDao.indexByCondition(client, "bee", "b")) {
			System.out.println(ss);
		}

	}
	
	@Test
	public void findTest() {

		for(Website ss : esDao.indexByContent(client, "beeid", "贴吧")) {
			System.out.println(ss);
		}

	}
	
	@Test
	public void deleteTest() {
		System.out.println(esDao.deleteDocument(client, "Vx4vxWcB-C2llrOLcGvJ"));
	}
	
	@Test
	public void updateTest() {
		Website website = new Website();
		website.setId("WR6LxWcB-C2llrOLOmuN");
		website.setUserId("beeid");
		website.setWebSiteName("elasticsearch");
		website.setWebSiteUrl("localhost:9300");
		System.out.println(esDao.updateDocument(client, new Gson().toJson(website), website.getId()));
	}
	
}
