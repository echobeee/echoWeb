package cn.echo.web.douyinSpider;

import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import cn.echo.web.pojo.VideoInfo;


/**
 * @toFixed: 并发未解决
 * @author echo
 *
 */
@Service
public class VideoService {

	private DouyinCrawl douyinCrawl = new DouyinCrawl();
	
	private ExecutorService executor = Executors.newFixedThreadPool(6);
	
	private synchronized DouyinCrawl getInstance() {
		return this.douyinCrawl;
	}
	
	/**
	 * 多线程
	 * @param url
	 * @param outstream
	 * @param backup 备用url
	 */
	public void asyncGetResponse(String url, OutputStream outstream, String backup) {
		executor.execute(new ThreadsGetResponse(getInstance(), outstream, url, backup));
	}
	
	public Map<String, VideoInfo> getVideoMap(String savepath) {
		return douyinCrawl.getVedioList(savepath);
	}

	/**
	 * 多线程的执行Runnable对象
	 * @author echo
	 *
	 */
	public class ThreadsGetResponse implements Runnable {
		
		DouyinCrawl crawler;
		OutputStream outstream;
		String url;
		String backup;

		public ThreadsGetResponse(DouyinCrawl crawler, OutputStream outstream, String url,String backup) {
			super();
			this.crawler = crawler;
			this.outstream = outstream;
			this.url = url;
			this.backup = backup;
		}

		@Override
		public void run() {
			crawler.getResponse(url, outstream,backup);
			
		}
		
	}
	
}
