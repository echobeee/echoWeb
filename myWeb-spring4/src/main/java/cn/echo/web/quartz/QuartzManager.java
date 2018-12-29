package cn.echo.web.quartz;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;

import cn.echo.utils.Date2Cron;
import cn.echo.web.pojo.Memo;

public class QuartzManager {
	private static String JOB_GROUP_NAME = "MEMO_JOBGROUP";
	private static String TRIGGER_GROUP_NAME = "MEMO_TRIGGERGROUP";

	/**
	 * 
	 * @param sched
	 *            调度器
	 * @param jobClass
	 *            Job的Class
	 * @param memo
	 *            备忘录
	 * @return 
	 */
	public static TriggerState newJob(Scheduler sched, @SuppressWarnings("rawtypes") Class jobClass, Memo memo) {
		// 作为SchedulerContext的map
		Map<String, Object> map = new HashMap<>();
		map.put("memo", memo);
		//
		// JobDetail jobDetail = new JobDetail(memo.getMemoId(), JOB_GROUP_NAME,
		// jobClass);
		// jobDetail.setJobDataMap(new JobDataMap(map));

		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(memo.getMemoId(), JOB_GROUP_NAME)
				.usingJobData(new JobDataMap(map)).build();

		// cron触发器
		CronTrigger trigger = TriggerBuilder.newTrigger()
											.withIdentity(memo.getMemoId(), TRIGGER_GROUP_NAME)
											.withSchedule(
													CronScheduleBuilder.cronSchedule(Date2Cron.getCron(memo.getSendTime())))
//											.withSchedule(
//													CronScheduleBuilder.cronSchedule(cron))
											.build();

		try {
			
			sched.scheduleJob(jobDetail, trigger);
			if (!sched.isShutdown()) {
				sched.start();
			}
			return sched.getTriggerState(new TriggerKey(memo.getMemoId(), TRIGGER_GROUP_NAME));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return TriggerState.ERROR;
		}

	}

	/**
	 * 修改一个任务的触发器时间
	 * 
	 * @param sched
	 * @param memo
	 * @return 
	 * 		STATE_BLOCKED 	4   阻塞
	 *		STATE_COMPLETE 	2   完成
	 * 		STATE_ERROR 	3   错误
	 *		STATE_NONE 	-1   不存在
	 *		STATE_NORMAL 	0  正常
	 *		STATE_PAUSED 	1  暂停
	 */
	public static TriggerState modifyJob(Scheduler sched, Memo memo) {
		try {
			CronTrigger trigger = (CronTrigger) sched.getTrigger(new TriggerKey(memo.getMemoId(), TRIGGER_GROUP_NAME));
			if (trigger == null) {
				return TriggerState.NONE;
			}
			// 获取原本的表达式
			String originalTime = trigger.getCronExpression();
			if (!originalTime.equalsIgnoreCase(Date2Cron.getCron(memo.getSendTime()))) {
				JobDetail jobDetail = sched.getJobDetail(new JobKey(memo.getMemoId(), JOB_GROUP_NAME));
				Class jobClass = jobDetail.getJobClass();
				removeJob(sched, memo.getMemoId(), memo.getMemoId());
				newJob(sched, jobClass, memo);
//				newJob(sched, jobClass, memo, "");
				sched.start();
			}
			return  sched.getTriggerState(new TriggerKey(memo.getMemoId(), TRIGGER_GROUP_NAME));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除任务
	 * 
	 * @param sched
	 * @param jobName
	 * @param triggerName
	 */
	public static void removeJob(Scheduler sched, String jobName, String triggerName) {
		try {
			
			sched.pauseTrigger(new TriggerKey(triggerName, TRIGGER_GROUP_NAME));
			sched.unscheduleJob(new TriggerKey(triggerName, TRIGGER_GROUP_NAME));
			sched.deleteJob(new JobKey(jobName, JOB_GROUP_NAME));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param sched
	 *            调度器
	 * @return 
	 * @Description:启动所有定时任务
	 * @Title: QuartzManager.java
	 */
	public static List<String> startJobs(Scheduler sched) {
		try {
			sched.start();
			List<JobExecutionContext> jobs = sched.getCurrentlyExecutingJobs();
			List<String> jobNames = new ArrayList<>();
			for (int i = 0; i < jobs.size(); i++) {
				jobNames.add(jobs.get(i).getTrigger().getJobKey().getName());
			}
			return jobNames;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param sched
	 *            调度器
	 * @Description:关闭所有定时任务
	 * @Title: QuartzManager.java
	 */
	public static void shutdownJobs(Scheduler sched) {
		try {
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void getAllJobs(Scheduler scheduler){
        try {
            
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();
                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();
                    System.out.println("[jobName] : " + jobName + " [groupName] : "
                        + jobGroup + " - " + nextFireTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
