package cn.echo.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class BaseController {

	@RequestMapping("/go/{folder}/{file}")
	public String baseUrl(@PathVariable("folder")String folder,
					@PathVariable("file")String file) {

		return "/" + folder + "/" + file;
	}

}
