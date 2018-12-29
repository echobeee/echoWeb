package cn.echo.web.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.print.DocFlavor.STRING;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mysql.fabric.xmlrpc.Client;

import cn.echo.utils.elasticsearch.EsClientBuilder;
import cn.echo.utils.elasticsearch.EsUtils;
import cn.echo.web.pojo.Website;
import cn.echo.web.service.FavoriteService;
import io.netty.handler.codec.http.HttpContentEncoder.Result;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	private EsUtils esDao;
	
	private TransportClient client;
	
	@PostConstruct
	public void getClient() {
		client = EsClientBuilder.getTransportClient();
	}
	
	@Override
	public String addWebsite(String userId, String webSiteName, String webSiteUrl) {
		String flag;
		// 别名不存在
		if(!esDao.checkIfExists(client, userId, webSiteName)) {
			XContentBuilder json = null;
			try {
				json = XContentFactory.jsonBuilder()
									.startObject()
									.field("userId", userId)
									.field("webSiteName", webSiteName)
									.field("webSiteUrl", webSiteUrl)
									.endObject();
				esDao.addIndex(client, json);
				flag =  "success";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = "fail";
			}
		} else {
			// 别名存在
			flag = "existed";
		}
		return flag;
		
	}

	@Override
	public List<String> autoComplete(String userId, String toComplete) {
		List<String> result = esDao.indexByCondition(client, userId, toComplete);
		return result;
	}

	@Override
	public Map<String, Object> findWebsitesByUserId(String userId, Integer page, Integer pageSize) {

		Map<String, Object> result = esDao.indexByUserId(client, userId, page, pageSize);

		return result;
	}

	@Override
	public List<Website> findWebsitesByContent(String userId, String content) {

		List<Website> result = esDao.indexByContent(client, userId, content);

		return result;
	}

	@Override
	public String deleteWebsite(String deleteId) {

		String result = esDao.deleteDocument(client, deleteId);

		if(result.equalsIgnoreCase("OK")) {
			return "success";
		}
		return "fail";
	}

	@PreDestroy
	public void closeConnect() {
		if(client != null) {
			client.close();
		}
	}

	@Override
	public String updateWebsite(Website website) {
		if(!esDao.checkIfExists(client, website.getUserId(), website.getWebSiteName(), website.getId())) {
			String json = new Gson().toJson(website);
			return esDao.updateDocument(client, json, website.getId());
		} else {
			return "existed";
		}
	}
	
}
