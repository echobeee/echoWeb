package cn.echo.web.test;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.echo.web.pojo.Comment;
import cn.echo.web.pojo.Memo;
import cn.echo.web.pojo.User;
import cn.echo.web.quartz.MemoJob;
import cn.echo.web.quartz.QuartzManager;
import cn.echo.web.service.BaseService;
import cn.echo.web.service.CommentService;
import cn.echo.web.service.EmailService;
import cn.echo.web.service.MemoService;
import cn.echo.web.service.impl.MemoServiceImpl;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring/applicationContext-*.xml")
public class TestAll {
	
	@Autowired
	CommentService commentService;
	
	
	@Autowired
	MemoService memoService;
	
	@Autowired 
	EmailService emailService;
	
	
	@Test
	public void test1(){
		Comment record = new Comment();
		record.setCommentId("2");
		record.setContent("测试评论");
		record.setCreateTime(new Date());
		record.setUserId("1");
		commentService.insert(record);
	}
	
	@Test
	public void test2(){
		Memo record = new Memo();
		record.setMemoId(UUID.randomUUID().toString());
		record.setEditTime(new Date());
		record.setMemoContent("提醒我去考试");
		record.setSendTime(new Date());
		record.setState(1);
		record.setUserId("1");
//		baseService.insert(record);
	}
	
	@Test
	public void test3(){
		System.out.println(memoService.findMemoByUseridWithDate("beeid", "2019-03").size());
	}
	
	@Test
	public void testQuartz() {
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring/applicationContext-quartz.xml");  
		 Scheduler scheduler = (Scheduler) ctx.getBean("schedulerFactoryBean");
		 System.out.println(scheduler);
		 Memo record = new Memo();
			record.setMemoId(UUID.randomUUID().toString());
			record.setEditTime(new Date());
			record.setMemoContent("提醒我去考试");
			record.setSendTime(new Date());
			record.setState(1);
			record.setUserId("1");
			//baseService.insert(record);
			//memoService.insert(record);
		 QuartzManager.newJob(scheduler, MemoJob.class, record);
		 
	}
	
	@Test
	public void test() {
		System.out.println(new Date());
	}
	
}
