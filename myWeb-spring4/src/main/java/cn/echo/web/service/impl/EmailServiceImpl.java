package cn.echo.web.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.echo.web.pojo.User;
import cn.echo.web.service.EmailService;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Override
	public void sendEmail(User user, String... args) {
		MimeMessage msg = null;
		try {
			msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
			helper.setFrom("echoWeb1@163.com");
			helper.setTo(user.getUserEmail());
			// smtp中不允许有中文字符，所以编码
			helper.setSubject(MimeUtility.encodeText("一封来自echoWeb的邮件", "utf-8",
					"B"));
			
			if(args[0].equals("active")) {
				helper.setText(getActiveMailText(user), true);
			} else if(args[0].equals("reset")) {
				helper.setText(getForgetPasswordMailText(user), true);
			} else if(args[0].equals("memo")) {
				helper.setText(getMemoMailText(user, args[1], args[2]), true);
			}
			
			
			// 添加内嵌文件，第1个参数为cid标识这个文件,第2个参数为资源
			helper.addInline("email", new File("e:/myWebPic/mail.gif")); // 附件内容
			helper.addInline("phone", new File("e:/myWebPic/phone.gif")); // 附件内容
			helper.addInline("clock", new File("e:/myWebPic/clock.gif")); // 附件内容
			helper.addInline("twitter", new File("e:/myWebPic/twti.gif")); // 附件内容
			helper.addInline("facebook", new File("e:/myWebPic/facebook.gif")); // 附件内容
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		mailSender.send(msg);

		System.out.println("邮件发送成功！");
	}

	/**
	 * 先设置激活码、激活状态，和token时间
	 * 获取激活邮件的邮件内容
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private String getActiveMailText(User user) throws Exception {
		// 设置激活的属性
		user.setActiCode(UUID.randomUUID().toString());
		user.setActiState(0);
		user.setTokenExptime(new Date());
		// 获得freemarker邮件模版
		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate("user/activeMail.ftl");

		// Freemarker通过map传user参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		String activeUrl = "localhost:8080/myWeb/user/activate?active="
				+ user.getActiCode() + "&id=" + user.getUserId();
		map.put("activeUrl", activeUrl);

		// 转换时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dated = sdf.format(
				user.getTokenExptime().getTime() + 24 * 60 * 60 * 1000L)
				.toString();
		map.put("expTime", dated);

		// //背景图片
		// map.put("bg", "https://s9.postimg.cc/408ihs0fz/DSC_2290.jpg");

		// 解析模版并替代动态数据，map中的数据将替换模版中的数据
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(
				template, map);
		return htmlText;
	}

	/**
	 * 获取重设密码的邮件内容
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private String getForgetPasswordMailText(User user) throws Exception {
		// 获得freemarker邮件模版
		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate("user/activeMail.ftl");

		// Freemarker通过map传user参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		
		// 重设码为原密码的md5一次循环
		String activeUrl = "localhost:8080/myWeb/user/reset?reset="
				+ new Md5Hash(user.getUserPassword()).toString() + "&id="
				+ user.getUserId();
		map.put("activeUrl", activeUrl);

		// 解析模版并替代动态数据，map中的数据将替换模版中的数据
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(
				template, map);
		return htmlText;

	}
	
	private String getMemoMailText(User user, String editTime, String content) throws Exception {
		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate("memo/remindMail.ftl");

		// Freemarker通过map传user参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		
		// 编辑时间
		map.put("editTime", editTime);
		
		// 备忘录内容
		map.put("content", content);
		
		// 解析模版并替代动态数据，map中的数据将替换模版中的数据
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(
				template, map);
		return htmlText;
	}

}
