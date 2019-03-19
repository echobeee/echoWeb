package cn.echo.utils.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 创建client实例连接elasticsearch，并提供获取
 * @author bee
 *
 */
@SuppressWarnings("resource")
public class EsClientBuilder {

	static Log log = LogFactory.getLog(EsClientBuilder.class);
	
	// 连接的elasticsearch集群地址
	public final static String host = "127.0.0.1";
	
	// 集群名字
	
	public final static String clusterName = "my-application";
	// 端口号 默认9300
	public final static int PORT = 9300;
	
	private static TransportClient client = null;
	private static Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", clusterName).build();
	
	// 批量提交
	private static BulkProcessor staticBulkProcessor = null;
	private static int clients = 0;
	
	private static ThreadLocal<TransportClient> tl = new ThreadLocal<>();
	
	// 单例
	public static synchronized TransportClient getTransportClient() {
		// 获取当前线程
		TransportClient cli = tl.get();
		if(cli != null) 
			return cli;

			try {
				// 获取client实例
				client = new PreBuiltTransportClient(settings)
						.addTransportAddress(new TransportAddress(InetAddress.getByName(host), PORT));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return client;
	}
	
//	 //【设置自动提交文档】
//	        public static BulkProcessor getBulkProcessor() {
//	            //自动批量提交方式
//	           if (staticBulkProcessor == null) {
//	                try {
//	                	TransportClient thisClient = getTransportClient();
//	                    staticBulkProcessor = BulkProcessor.builder(thisClient,
//	                            new BulkProcessor.Listener() {
//	                                @Override
//	                                public void beforeBulk(long executionId, BulkRequest request) {
//	                                    //提交前调用
//	    //                                System.out.println(new Date().toString() + " before");
//	                                }
//	    
//	                                @Override
//	                                public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
//	                                    //提交结束后调用（无论成功或失败）
//	    //                                System.out.println(new Date().toString() + " response.hasFailures=" + response.hasFailures());
//	                                    log.info( "提交" + response.getItems().length + "个文档，用时"
//	                                            + response.getTook() + "MS" + (response.hasFailures() ? " 有文档提交失败！" : ""));
//	    //                                response.hasFailures();//是否有提交失败
//	                                }
//	    
//	                                @Override
//	                                public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
//	                                    //提交结束且失败时调用
//	                                    log.error( " 有文档提交失败！after failure=" + failure);
//	                                }
//	                            })
//	                            
//	                            .setBulkActions(1000)//文档数量达到1000时提交
//	                            .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))//总文档体积达到5MB时提交 //
//	                          .setFlushInterval(TimeValue.timeValueSeconds(5))//每5S提交一次（无论文档数量、体积是否达到阈值）
//	                          .setConcurrentRequests(1)//加1后为可并行的提交请求数，即设为0代表只可1个请求并行，设为1为2个并行
//	                          .build();
//	  //                staticBulkProcessor.awaitClose(10, TimeUnit.MINUTES);//关闭，如有未提交完成的文档则等待完成，最多等待10分钟
//	                    thisClient.close();
//	              } catch (Exception e) {//关闭时抛出异常
//	                  e.printStackTrace();
//	              }
//	          }//if
//	  
//	  
//	  
//	  
//	         return staticBulkProcessor;
//	     }
}
