package cn.echo.web.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;


public class newTest {
	
	@Test
	public void test() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		System.out.println(date);
	}
}
