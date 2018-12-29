package cn.echo.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统全局异常处理器
 * @author Administrator
 *
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		// 用户异常
		UserException userException = null;
		
		// 备忘录异常
		MemoException memoException = null;
		
		String message = null;
		
		ModelAndView modelAndView = new ModelAndView();
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.addHeader("Content-Type", "text/html; charset=utf-8");
		
		 //如果该 异常类型是系统 自定义的异常，直接取出异常信息，在错误页面展示。  
		if(ex instanceof UserException) {
			userException = (UserException) ex;
			message = userException.getMessage();

			//将错误信息传到页面  
			modelAndView.addObject("message", message);
			
			//user错误界面
			modelAndView.setViewName("error");
		} else if(ex instanceof MemoException) {
			memoException = (MemoException) ex;
			
			message = memoException.getMessage();

			//将错误信息传到页面  
			modelAndView.addObject("message", message);
			
			//memo错误界面
			modelAndView.setViewName("memoError");
			
		}
 		else {
			// 如果为系统异常，则设置为系统内部错误
			userException = new UserException("系统内部错误");
			
			//system错误界面
			modelAndView.setViewName("systemError");
		}

		
		return modelAndView;
	}

}
