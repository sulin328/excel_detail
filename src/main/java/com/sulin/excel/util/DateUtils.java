package com.sulin.excel.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
	public static SimpleDateFormat YYMM = new SimpleDateFormat("yyyy年MM月"); 
	public static SimpleDateFormat MD = new SimpleDateFormat("M.d"); 
	public static SimpleDateFormat YYYYMD = new SimpleDateFormat("yyyy/M/d"); 
	//判断日期是否是所在月的第一天
	public static boolean isFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    return calendar.get(Calendar.DAY_OF_MONTH) == 1;
	}
	
	public static String getPreMonthOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.MONTH, -1);
	    return YYMM.format(calendar.getTime());
	}
	
	public static void main(String[] args) {
		System.out.println(MD.format(new Date()));
	}
}
