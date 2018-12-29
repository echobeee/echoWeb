package cn.echo.web.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import cn.echo.web.pojo.Memo;
import cn.echo.web.quartz.MemoJob;
import cn.echo.web.quartz.QuartzManager;
import cn.echo.web.service.MemoService;

/**
 * 备忘录Controller层
 * @author echo
 *
 */
@RequestMapping("/memo")
@Controller
public class MemoController extends BaseController {
	
	@Autowired
	@Qualifier("schedulerFactoryBean")
	Scheduler scheduler;
	
	@Autowired
	MemoService memoService;
	
	private List<String> names;
	
	@InitBinder
    public void initDate(WebDataBinder binder) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      dateFormat.setLenient(false);
      binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        //binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss")); // Spring 4.2之后的写法
    }
	
	@PostConstruct
	public void startJobs() {
		for(Memo memo : memoService.findAllMemos()) {
			QuartzManager.newJob(scheduler, MemoJob.class, memo);
		}
		names = QuartzManager.startJobs(scheduler);
		
	}
	
	@RequestMapping("/addMemo.do")
	@ResponseBody
	public String addMemo(Memo memo ) {
		// ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring/applicationContext-quartz.xml");  
		// Scheduler scheduler = (Scheduler) ctx.getBean("schedulerFactoryBean");
		memo.setEditTime(new Date());
		memo.setMemoId(UUID.randomUUID().toString());
		memo.setState(0);// 设置成邮件未发送的状态
		
		memoService.insert(memo);
		
		return QuartzManager.newJob(scheduler, MemoJob.class, memo).toString();
		 
	}
	
	@RequestMapping("/removeMemo.do/{memoId}")
	@ResponseBody
	public String removeMemo(@PathVariable("memoId")String memoId) {
		
		memoService.deleteByPrimaryKey(memoId);
		QuartzManager.removeJob(scheduler, memoId, memoId);
		return "success";
	}
	
	@RequestMapping("/myMemos.do")
	@ResponseBody
	public PageInfo<Memo> findMyMemos(@RequestParam(required=true)String userId, 
							@RequestParam(required=false,defaultValue="1") int pageNum,
							@RequestParam(required=false,defaultValue="10")int pageSize) {
		
		return memoService.findAllByUserid(userId, pageNum, pageSize);
	}
	
	/**
	 * 根据用户的某个月份查询该月份的所有备忘录
	 * @param userId
	 * @param date
	 * 		format ： YYYY-mm （01-12）
	 * @return
	 */
	@RequestMapping("/myMonthMemos")
	@ResponseBody
	public List<Memo> findMemosByUserIdWithDate(String userId, String date) {
		for(String name : names)
			System.out.println(name);
		return memoService.findMemoByUseridWithDate(userId, date);
	}
	
	@RequestMapping("/updateMemo.do")
	@ResponseBody
	public String modifyMemo(Memo memo) {
		memo.setState(0);
		memoService.updateByPrimaryKey(memo);
		
		// 获取完整的memo记录
		return QuartzManager.modifyJob(scheduler, memoService.selectByPrimaryKey(memo.getMemoId())).toString();
	}
	
	@RequestMapping("/getMemo")
	@ResponseBody
	public Memo findMemo(@RequestParam(required=true,name="res") String memoId) {
		return memoService.selectByPrimaryKey(memoId);
		
	}
	
	public void getAllJobs() {
		QuartzManager.getAllJobs(scheduler);
	}
}
