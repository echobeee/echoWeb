package cn.echo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.propertyeditors.PropertiesEditor;

public class MyDateEditor extends PropertiesEditor {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		  Date date=null;
          SimpleDateFormat sdf = getDateFormat(text);
          try {
              date = sdf.parse(text);
              setValue(date);
          } catch (ParseException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
	}

	private SimpleDateFormat getDateFormat(String source) {
		 SimpleDateFormat sf = new SimpleDateFormat();

		//代表格式为yyyy-MM-dd

         if(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$", source)){
             sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
         }

		//代表格式为yyyy/MM/dd

         if(Pattern.matches("^\\d{4}/\\d{2}/\\d{2}$", source)){
             sf = new SimpleDateFormat("yyyy/MM/dd");
         }

		//代表格式为yyyyMMdd

		   if(Pattern.matches("^\\d{4}\\d{2}\\d{2}$", source)){
		             sf = new SimpleDateFormat("yyyyMMdd");
		         }
		         return sf;
	}

	
	
}
