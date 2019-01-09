package cn.echo.web.quartz;

import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.echo.web.douyinSpider.DouyinCrawl;
import cn.echo.web.douyinSpider.Signature;
import cn.echo.web.pojo.Memo;

/**
 * 每隔4个小时 获取新的token
 * @author echo
 *
 */
public class ReconnectJob implements Job {


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		Map<String, Object> dataMap = context.getJobDetail().getJobDataMap().getWrappedMap();
		DouyinCrawl douyinCrawl = (DouyinCrawl) dataMap.get("crawl");
		
		String token = new Signature().getToken();
		if(token.length() != 0) {
			douyinCrawl.setToken(token);
			douyinCrawl.stopConnecting();
		}
	}

}
