package cn.echo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Date2Cron {
	/*** 
     *  
     * @param date 
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static String formatDateByPattern(Date date,String dateFormat){  
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);  
        String formatTimeStr = null;  
        if (date != null) {  
            formatTimeStr = sdf.format(date);  
        }  
        return formatTimeStr;  
    }  
/*** 
     * convert Date to cron ,eg.  "0 07 10 15 1 ? 2016" 
     * @param date  : 时间点 
     * @return 
     */  
    public static String getCron(java.util.Date  date){  
        String dateFormat="ss mm HH dd MM ? yyyy";  
        return formatDateByPattern(date, dateFormat);  
    }  
}
