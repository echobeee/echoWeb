package cn.echo.utils.elasticsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry.Option;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import cn.echo.web.pojo.Website;

@Repository
public class EsUtils {

	// 索引名 - 相当于数据库
	private String indexName = "favorites";
	// 类型名 - 相当于table
	private String typeName = "websites";

	/**
	 * 插入数据
	 * 
	 * @param client
	 * @param json
	 *            传入的json Object
	 * @param userId
	 *            用户id
	 * @param webSiteName
	 *            网站别名
	 * @return 返回字符串 OK为添加成功，否则为失败
	 * 
	 */
	public String addIndex(TransportClient client, XContentBuilder json) {

		return client.prepareIndex(indexName, typeName).setSource(json).get().status().toString();

	}
	
	/**
	 * 批量添加 
	 * 一般用于测试或者初始化、用户级别基本不用
	 * @param jsons XContentBuilder的对象列表
	 */
//	public void bulkAddIndex(List<XContentBuilder> jsons) {
//		BulkProcessor processor = EsClientBuilder.getBulkProcessor();
//		for(XContentBuilder json : jsons) {
//			processor.add(new IndexRequest(indexName, typeName).source(json));
//		}
//	}

	/**
	 * 检查别名是否已经存在
	 * 
	 * @param client
	 * @param userId
	 * @param webSiteName
	 * @return
	 */
	public boolean checkIfExists(TransportClient client, String userId, String webSiteName, String fid) {
		// BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		// termQuery大小写敏感
		// mustQuery.must(QueryBuilders.termQuery("userId", userId));
		// mustQuery.must(QueryBuilders.matchPhraseQuery("webSiteName", webSiteName));
		//
		// SearchResponse searchResponse =
		// client.prepareSearch(indexName).setTypes(typeName).setQuery(mustQuery).execute()
		// .actionGet();
		// SearchHits hits = searchResponse.getHits();
		@SuppressWarnings("unchecked")
		List<Website> list = (List<Website>) indexByUserId(client, userId, 0, -1).get("websiteList");
		for (Website webs : list) {
			if (webs.getWebSiteName().equals(webSiteName)) {
				if(!webs.getId().equalsIgnoreCase(fid)) // 不是同一個
					return true;
			}
		}
		return false;
	}
	
	public boolean checkIfExists(TransportClient client, String userId, String webSiteName) {
		// BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		// termQuery大小写敏感
		// mustQuery.must(QueryBuilders.termQuery("userId", userId));
		// mustQuery.must(QueryBuilders.matchPhraseQuery("webSiteName", webSiteName));
		//
		// SearchResponse searchResponse =
		// client.prepareSearch(indexName).setTypes(typeName).setQuery(mustQuery).execute()
		// .actionGet();
		// SearchHits hits = searchResponse.getHits();
		@SuppressWarnings("unchecked")
		List<Website> list = (List<Website>) indexByUserId(client, userId, 0, -1).get("websiteList");
		for (Website webs : list) {
			if (webs.getWebSiteName().equals(webSiteName)) {
					return true;
			}
		}
		return false;
	}

	/**
	 * 根据userId查询某个用户的收藏夹网页
	 * 并分页
	 * @param client
	 * @param userId
	 * @param page 当前页 【从1开始计数，若为0则查询所有】
	 * @param pageSize 页面的网站数 【为-1时则查询所有】
	 * @return map 包含字段：
	 * 		currentPage 当前页数
	 * 		records 总的记录数
	 * 		pages 总页数
	 * 		websiteList 网页Website对象列表
	 */
	public Map<String, Object> indexByUserId(TransportClient client, String userId, Integer page, Integer pageSize) {
		
		Map<String, Object> result = new HashMap<>();
		
		QueryBuilder qBuilder = QueryBuilders.termQuery("userId", userId);
		SearchRequestBuilder builder = client.prepareSearch(indexName).setTypes(typeName).setQuery(qBuilder);
		
		// 索引开始计数
		int currentIndex = (page - 1) * pageSize; 
		result.put("currentPage", page);
		
		// 总记录数
		long totalWebsites = client.prepareSearch(indexName).setTypes(typeName).setQuery(qBuilder).get().getHits().totalHits;
		result.put("records", totalWebsites);
		
		// 总页数
		long totalPages = 0;
		if(pageSize == 0) {
			totalPages = 0;
		}
		else {
			totalPages = totalWebsites % pageSize == 0 ? totalWebsites/pageSize : totalWebsites/pageSize + 1;
		}
		result.put("pages", totalPages);
		
		
		SearchResponse response = (pageSize == -1) ? 
									builder.execute().actionGet() 
									: builder.setFrom(currentIndex).setSize(pageSize).execute()
									.actionGet();
				
		SearchHits hits = response.getHits();
		List<Website> list = new ArrayList<>();
		if (hits.totalHits > 0) {
			for (SearchHit hit : hits) {
				list.add(new Gson().fromJson(hit.getSourceAsString(), Website.class).setId(hit.getId()));
			}
		}
		result.put("websiteList", list);

		return result;
	}

	/**
	 * 根据前缀的自动补全
	 * 
	 * @param client
	 * @param userId
	 * @param toComplete
	 *            用户输入的前缀
	 * @return 自动补全的结果
	 */
	public List<String> indexByCondition(TransportClient client, String userId, String toComplete) {

		// 别名自动补全
		String suggestName = "webSiteName.suggest";
		// url自动补全
		String suggestUrl = "webSiteUrl.suggest";

		// 这是返回的搜索结果
		// Map<String, Object> msgMap = new HashMap<>();

		// 查询用户id所属的网站
		QueryBuilder query = QueryBuilders.termQuery("userId", userId);

		// 搜索的index和type的request
		SearchRequestBuilder srBuilder = client.prepareSearch(indexName).setTypes(typeName).setQuery(query);

		// 设置搜索字段
		CompletionSuggestionBuilder completionSuggestionBuilder1 = new CompletionSuggestionBuilder(suggestName)
				.prefix(toComplete).size(10);
		CompletionSuggestionBuilder completionSuggestionBuilder2 = new CompletionSuggestionBuilder(suggestUrl)
				.prefix(toComplete).size(10);

		SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion(suggestName, completionSuggestionBuilder1)
				.addSuggestion(suggestUrl, completionSuggestionBuilder2);

		srBuilder.suggest(suggestBuilder);
		// 进行搜索
		SearchResponse suggestResponse = srBuilder.execute().actionGet();

		// 结果集
		Set<String> result = new HashSet<>();

		// 处理返回的url【自动补全】结果
		List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> entries = suggestResponse
				.getSuggest().getSuggestion(suggestName).getEntries();

		// 处理返回的名称【自动补全】结果
		List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> entries1 = suggestResponse
				.getSuggest().getSuggestion(suggestUrl).getEntries();

		SearchHits hits = suggestResponse.getHits();
		for (SearchHit hit : hits) {
			// 获取每个网址的名称
			String webSiteName = (String) hit.getSourceAsMap().get("webSiteName");

			for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> option : entries) {
				List<? extends Option> options = option.getOptions();
				for (Suggest.Suggestion.Entry.Option pp : options) {
					// 比较【自动补全】与网站名称是否相同，不相同即没有该【自动补全建议】
					if (pp.getText().toString().equals(webSiteName)) {
						result.add(webSiteName);
					}
				}
			}

			// 获取url
			String webSiteUrl = (String) hit.getSourceAsMap().get("webSiteUrl");
			for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> option : entries1) {
				List<? extends Option> options = option.getOptions();
				for (Suggest.Suggestion.Entry.Option pp : options) {
					// 比较【自动补全】与网站url是否相同，不相同即没有该【自动补全建议】
						String text = pp.getText().toString();
						if (webSiteUrl.contains(text)) {
							result.add(webSiteUrl);
							
						}
				}
			}
		}

		List<String> resultList = new ArrayList<>(result);
		System.out.println("resultlist: " + resultList);
		return resultList;
	}

	/**
	 * 根据用户输入的名称来查找网站
	 * 
	 * @param client
	 * @param userId
	 * @param content
	 *            用户输入
	 * @return 带有高亮片段的website对象列表
	 */
	public List<Website> indexByContent(TransportClient client, String userId, String content) {

		// 执行bool查询
		BoolQueryBuilder query = QueryBuilders.boolQuery();

		// must为该用户id
		query.must(QueryBuilders.termQuery("userId", userId));
		// should查询 相当于 or
		query.should(QueryBuilders.multiMatchQuery(content,"webSiteName", "webSiteUrl" ));
		//query.should(QueryBuilders.termQuery("webSiteUrl", content));
		SearchRequestBuilder builder = client.prepareSearch(indexName).setTypes(typeName)
				/* 设置查询类型：1.SearchType.DFS_QUERY_THEN_FETCH 精确查询； 2.SearchType.SCAN 扫描查询,无序 */
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(query)
				// .setFrom(new Long(page.getStartPos()).intValue()) // 分页
				.setSize(10);

		// 高亮显示搜索部分
		HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
		highlightBuilder.preTags("<span style=\"color:red\">");
		highlightBuilder.postTags("</span>");

		builder.highlighter(highlightBuilder);

		SearchResponse response = builder.execute().actionGet();

		SearchHits hits = response.getHits();
		List<Website> result = new ArrayList<>();

		// 处理返回的高亮片段
		for (SearchHit hit : hits) {
			// result.add(new Gson().fromJson(hit.getSourceAsString(),Website.class));
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			HighlightField field = highlightFields.get("webSiteName");
			if (field != null) {
				Text[] fragments = field.fragments();
				String highlights = "";
				// 得到高亮片段
				for (Text text : fragments) {
					highlights += text;
				}
				Website website = new Gson().fromJson(hit.getSourceAsString(), Website.class).setId(hit.getId());
				website.setWebSiteName(highlights);
				result.add(website);
			}
		}
		return result;
	}

	/**
	 * 根据网站的id来删除用户的某个收藏夹网站
	 * 
	 * @param client
	 * @param userId
	 * @param deleteId
	 *            用户网站的id
	 * @return OK为删除成功，否则失败
	 */
	public String deleteDocument(TransportClient client, String deleteId) {
		DeleteResponse response = client.prepareDelete(indexName, typeName, deleteId).execute().actionGet();
		return response.status().toString();
	}
	
	/**
	 * @dest 更新别名或者网址
	 * @param client
	 * @param json
	 * 			更新的json字符串
	 * @param changeId
	 * 			用户网站的id
	 * @return
	 * 		  OK为更新成功，否则失败
	 */
	public String updateDocument(TransportClient client, String json, String changeId) {
		UpdateResponse response = client.prepareUpdate(indexName, typeName, changeId).setDoc(json, XContentType.JSON).execute().actionGet();
		return response.status().toString();
	}

}
