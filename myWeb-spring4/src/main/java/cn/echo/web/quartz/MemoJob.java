package cn.echo.web.quartz;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.tools.ant.types.resources.comparators.Date;
import org.hibernate.validator.constraints.Email;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import cn.echo.web.pojo.Memo;
import cn.echo.web.service.EmailService;
import cn.echo.web.service.MemoService;
import cn.echo.web.service.UserService;

// 实现非并行调度， 即串行调度
//@DisallowConcurrentExecution

public class MemoJob implements Job {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MemoService memoService;
	
	@Autowired
	private UserService userService;
	
	private final Logger logger = Logger.getLogger(MemoJob.class);
	
	/*
	 * 定时器执行任务
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		// 为了让spring自动注入生效
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		Map<String, Object> dataMap = jobExecutionContext.getJobDetail().getJobDataMap().getWrappedMap();
		Memo memo = (Memo) dataMap.get("memo");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 发送邮件通知备忘
		emailService.sendEmail(userService.selectByPrimaryKey(memo.getUserId()), "memo", sdf.format(memo.getEditTime()), memo.getMemoContent());
		
		logger.debug("memoId is [" + memo.getMemoId() + "], user id is [" + memo.getUserId() + "] memo has been sent in " + sdf.format(new java.util.Date()));
		
		// 设置为已发送邮件的状态
		memo.setState(1);
		memoService.updateByPrimaryKey(memo);
	}

}
