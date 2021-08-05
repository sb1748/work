package com.jxszj.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtils {
	
	
	public static String FORMAT_SHORT = "yyyy/MM/dd";
	
	public static String FORMAT_LONG = "yyyy/MM/dd HH:mm:ss";
	
	public static String FORMAT_1_LONG = "yyyy-MM-dd HH:mm:ss";
	
	public static String FORMAT_STRING = "yyyyMMddHHmmss";
	
	public static String FORMAT_STRING1 = "HHmmssSSS";
	
	public static String FORMAT_INTEGER = "yyyyMMdd";
	
	public static String FORMAT_1_STRING = "yyyy-MM-dd";
	
	public static String FORMAT_2_STRING = "yyyy年MM月dd日";
	public static String FORMAT_2_1_STRING = "yyyy年M月d日";
	
	public static String FORMAT_3_STRING = "yyyy-MM-dd HH:mm";
	
	public static String FORMAT_STRING_YEAR = "yyyy年";
	
	public static String FORMAT_STRING_MINUTE = "yyyy年MM月";
	public static String FORMAT_STRING1_MINUTE = "yyyy年M月";
	
	public static String FORMAT_STRING_MINUTE1 = "yyyy年M月";
	
	public static String FORMAT_STRING_MINUTE2 = "MM月dd日";
	
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    String转换成Date
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年3月23日 上午9:39:12
	 *@param str
	 *@return
	 *@throws ParseException
	 *</pre>
	 */
	public static Date getStringToDate_T(String str) throws ParseException{
		//此格式只有  jdk 1.7才支持  yyyy-MM-dd‘T‘HH:mm:ss.SSSXXX  
	    SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss");
	    Date date =  sdf.parse(str);
	    return date;
	}
	
	public static String getStringToDate_T(String str,String format) throws ParseException{
		if("".equals(str)){
			return "";
		}
		//此格式只有  jdk 1.7才支持  yyyy-MM-dd‘T‘HH:mm:ss.SSSXXX  
	    SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss");
	    Date date =  sdf.parse(str);
	    SimpleDateFormat formatter = new SimpleDateFormat(format);  
	    String dateString = formatter.format(date);  
	    return dateString; 
	}
	
	
	public static Integer getStringDateToInteger(String str,String format) throws ParseException{
		//此格式只有  jdk 1.7才支持  yyyy-MM-dd‘T‘HH:mm:ss.SSSXXX  
	    SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss");
	    Date date =  sdf.parse(str);
	    SimpleDateFormat formatter = new SimpleDateFormat(format);  
	    String dateString = formatter.format(date);  
	    return Integer.valueOf(dateString); 
	}
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将yyyy-MM-dd'T'HH:mm:ss.SSS Z格式的时间转换成date
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年3月26日 下午2:55:34
	 *@param time
	 *@return
	 *@throws ParseException
	 *</pre>
	 */
	public static Date getStringToDate_Z(String time) throws ParseException{
		time = time.replace(" ", "").replace("Z", " UTC");//注意是空格+UTC
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
		Date date = format.parse(time );
		return date;
	}
	
	/**
	 * 
	 *<pre>
	 *<b>Description:</b> 
	 *    将UTC时间转成时间戳
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2021年7月6日 下午3:12:24
	 *@param time
	 *@return
	 *</pre>
	 */
	public static Long getLongDate(String time){
		time = time.replace(" ", "").replace("Z", " UTC");//注意是空格+UTC
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
		Long longDate=format.parse(time,new ParsePosition(0)).getTime();
		return longDate/1000L;
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    String转换成yyyy/MM/dd
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年6月15日 上午9:42:29
	 *@param str
	 *@return
	 *@throws ParseException
	 *</pre>
	 */
	public static String getStringFormat_Z(String time,String format) throws ParseException{
		time = time.replace(" ", "").replace("Z", " UTC");//注意是空格+UTC
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
		Date date = dateFormat.parse(time );
	    SimpleDateFormat formatter = new SimpleDateFormat(format);  
	    String dateString = formatter.format(date);  
	    return dateString; 
	}
	
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    Date转换成String
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年3月23日 上午9:39:50
	 *@param date
	 *@return
	 *@throws ParseException
	 *</pre>
	 */
	public static String getDateToString(Date date) throws ParseException{
		if(date==null || "".equals(date)){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/dd");
	    String str=sdf.format(date);
	    return str;
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将yyyy-MM-dd'T'HH:mm:ss格式转换成yyyy-MM-dd HH:mm格式
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月27日 上午11:05:50
	 *@param Date_T
	 *@return
	 *@throws ParseException
	 *</pre>
	 */
	public static String getDate_TToString(String Date_T) throws ParseException{
		 SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss");
		 Date date =  sdf.parse(Date_T);
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 String str=sdf1.format(date);
		 return str;
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将yyyy-MM-dd'T'HH:mm:ss格式转换成yyyy-MM-dd HH:mm:ss格式
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年5月15日 上午10:50:30
	 *@param Date_T
	 *@return
	 *@throws ParseException
	 *</pre>
	 */
	public static String getDate_T_ToString(String Date_T) throws ParseException{
		 SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss");
		 Date date =  sdf.parse(Date_T);
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String str=sdf1.format(date);
		 return str;
	}

	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将yyyy-MM-dd格式的时间转换成Date
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月4日 下午2:17:35
	 *@param time
	 *@return
	 *</pre>
	 */
	public static Date getStringToDate(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Date date=null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
	    return date;
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    获取当前月份值
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月4日 下午2:15:56
	 *@return
	 *</pre>
	 */
	public static int getMonth(){
		Calendar calendar=Calendar.getInstance();
		//获得当前时间的月份，月份从0开始所以结果要加1
		int month=calendar.get(Calendar.MONTH)+1;
		return month;
	}
	
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    获取前一个月的第一天
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月8日 下午5:38:30
	 *@return
	 *</pre>
	 */
	public static String getOneMonthBeforeMin(){
		Calendar ca = Calendar.getInstance();//得到一个Calendar的实例 
		ca.setTime(new Date()); //设置时间为当前时间 
		ca.add(Calendar.MONTH, -1);
		ca.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		Date lastMonth = ca.getTime(); //结果
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String str=sdf.format(lastMonth);
	    return str;
	
	}
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    获取前两个月的最后一天
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月23日 下午3:20:08
	 *@return
	 *</pre>
	 */
	public static String getTwoMonthBeforeMax(){
		Calendar ca = Calendar.getInstance();//得到一个Calendar的实例 
		ca.setTime(new Date()); //设置时间为当前时间 
		ca.add(Calendar.MONTH, -2);
		ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
		Date lastMonth = ca.getTime(); //结果
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String str=sdf.format(lastMonth);
	    return str;
	
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *   获取指定时间第一天
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月6日 下午5:10:41
	 *@param time
	 *@return
	 *</pre>
	 */
	public static String getStringToMonthOneDay(String time){
	 if (time == null || time.equals("")) {  
            return null;  
        }  
        Date date=null;  
        DateFormat dateFormat=null;
        try{  
            dateFormat=new SimpleDateFormat("yyyy-MM-dd");  
            date=dateFormat.parse(time);  
        }catch(Exception e){  
              
        }  
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
    	calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
    	return dateFormat.format(calendar.getTime()); 
	}
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    获取当前系统时间第一天
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2020年1月10日 下午12:15:15
	 *@return
	 *</pre>
	 */
	public static String getStringToMonthOneDay(){
		Calendar cale = Calendar.getInstance(); 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		cale = Calendar.getInstance();  
        cale.add(Calendar.MONTH, 0);  
        cale.set(Calendar.DAY_OF_MONTH, 1);  
        String firstday = format.format(cale.getTime());
        return firstday;
	}
	
	/**
	 * 
	 * <pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    获取当前系统时间月份第一天
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年9月24日 下午12:31:59
	 *&#64;param time
	 *&#64;return
	 * </pre>
	 */
	public static String getStringToOneDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gc.getTime());
		return day_first;
	}
	
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将当前系统时间转换成yyyy-MM-dd时间格式
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月7日 上午10:07:59
	 *@return
	 *</pre>
	 */
	public static String getNowDateToString(){
		Date currentTime = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    String dateString = formatter.format(currentTime);  
	    return dateString; 
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将当前系统时间转换成yyyy-MM-dd时间格式再返回Date
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月7日 上午10:07:59
	 *@return
	 *</pre>
	 */
	public static Date getNowDate(){
		Date currentTime = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    String dateString = formatter.format(currentTime);  
	    Date date =null;
		try {
			date = formatter.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将当前系统时间转换成指定格式
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2020年1月10日 上午11:48:08
	 *@param format
	 *@return
	 *</pre>
	 */
	public static String getNowDateToString(String format){
		Date currentTime = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat(format);  
	    String dateString = formatter.format(currentTime);  
	    return dateString; 
	}

	
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将yyyy-MM-dd格式转换成UTC
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月23日 上午10:47:41
	 *@param date
	 *@return
	 *</pre>
	 */
	public static String getUTC(String date){
		 //Z代表UTC统一时间:2017-11-27T03:16:03.944Z
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

       SimpleDateFormat dayformat = new SimpleDateFormat("yyyy-MM-dd");
       //先将年月日的字符串日期格式化为date类型
       Date day=null;
		try {
			day = dayformat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
       String str2= format.format(day);
       return str2;
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    获取昨天的日期
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年5月5日 下午2:08:40
	 *@return
	 *</pre>
	 */
	public static  String getYesterday(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
		return yesterday;
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    获取当天的开始时间
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年5月13日 下午3:10:45
	 *@return
	 *</pre>
	 */
	public static Date getTodayStartTime() {
	       Calendar todayStart = Calendar.getInstance();
	       todayStart.set(Calendar.HOUR_OF_DAY, 0);
	       todayStart.set(Calendar.MINUTE, 0);
	       todayStart.set(Calendar.SECOND, 0);
	       return todayStart.getTime();
	}
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    获取当天的结束时间
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年5月13日 下午3:11:03
	 *@return
	 *</pre>
	 */
	public static Date getTodayEndTime() {
	     Calendar todayEnd = Calendar.getInstance();
	     todayEnd.set(Calendar.HOUR_OF_DAY, 23);
	     todayEnd.set(Calendar.MINUTE, 59);
	     todayEnd.set(Calendar.SECOND, 59);
	     return todayEnd.getTime();
	 }
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    13位时间戳转换成Date
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2020年5月16日 下午2:44:41
	 *@return
	 *</pre>
	 */
	public static Date getLongToDate(String longDate){
		 Date d = new Date(Long.parseLong(longDate.substring(longDate.indexOf("(")+1, longDate.indexOf(")"))));
		 return d;
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    13位时间戳转换成字符转
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2020年5月16日 下午2:44:41
	 *@return
	 *</pre>
	 */
	public static String getLongToString(String longDate,String format){
		if(longDate==null || longDate.equals("")){
			return "";
		}
		Date d=new Date();
		if(longDate.contains("(")){
			 d = new Date(Long.parseLong(longDate.substring(longDate.indexOf("(")+1, longDate.indexOf(")"))));
		}else{
			 d = new Date(Long.parseLong(longDate));
		}
		 SimpleDateFormat sf = new SimpleDateFormat(format);
		 return sf.format(d);
	}
	
//	  public static int getBetweenDays() {
//		    int day = 0;
//		    try {
//		      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//		      Calendar cal = Calendar.getInstance();
//
//		      cal.setTime(sdf.parse(getNowDateToString()));
//
//		      long time1 = cal.getTimeInMillis();
//
//		      cal.setTime(sdf.parse("2020-07-01"));
//
//		      long time2 = cal.getTimeInMillis();
//
//		      long between_days = (time2 - time1) / 86400000L;
//
//		      day = Integer.parseInt(String.valueOf(between_days));
//		    } catch (Exception e) {
//		      e.printStackTrace();
//		    }
//		    return day;
//		  }
//	  
	  
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    获取两个日期相差的天数
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2020年6月18日 下午2:43:02
	 *@param date1
	 *@param date2
	 *@return
	 *</pre>
	 */
	    public static long getBetweenDays(Date date1,Date date2){
	    	long day=0;
	    	try {
		        day = (date2.getTime() - date1.getTime()) / (1000L*3600L*24L);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return day;
	    }
	    
		/**
		 * 
		 *<pre>
		 *<b>.</b>
		 *<b>Description:</b> 
		 *    将yyyy-MM-dd HH:mm:ss格式转换成yyyy-MM-dd格式
		 *<b>Author:</b> yanwei
		 *<b>Date:</b> 2019年5月15日 上午10:50:30
		 *@param Date_T
		 *@return
		 *@throws ParseException
		 *</pre>
		 */
		public static String getDateStringToString(String strDate) throws ParseException{
			 SimpleDateFormat sdf = new SimpleDateFormat (FORMAT_1_LONG);
			 Date date =  sdf.parse(strDate);
			 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			 String str=sdf1.format(date);
			 return str+"T00:00:00.000Z";
		}
		
		
		/**
		 * 
		 *<pre>
		 *<b>.</b>
		 *<b>Description:</b> 
		 *    将yyyy-MM-dd HH:mm:ss格式转换成yyyy-MM-dd格式
		 *<b>Author:</b> yanwei
		 *<b>Date:</b> 2019年5月15日 上午10:50:30
		 *@param Date_T
		 *@return
		 *@throws ParseException
		 *</pre>
		 */
		public static String getDateStringToStringFormat(String strDate,String format) throws ParseException{
			 SimpleDateFormat sdf = new SimpleDateFormat (FORMAT_1_LONG);
			 Date date =  sdf.parse(strDate);
			 SimpleDateFormat sdf1 = new SimpleDateFormat(format);
			 String str=sdf1.format(date);
			 return str;
		}
		
	    /**
	     * 
	     *<pre>
	     *<b>.</b>
	     *<b>Description:</b> 
	     *    获取指定日期指定天数的日期
	     *<b>Author:</b> yanwei
	     *<b>Date:</b> 2020年6月18日 下午2:38:40
	     *@param date
	     *@param day
	     *@return
	     *</pre>
	     */
		public static Date getDate(Date date, long day){
			long time=0;
			try {
				time = date.getTime(); // 得到指定日期的毫秒数

				day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数

				time += day; // 相加得到新的毫秒数
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new Date(time); // 将毫秒数转换成日期

		}
		
		/**
		 * 
		 *<pre>
		 *<b>Description:</b> 
		 *    获取指定日期指定天数的日期
		 *<b>Author:</b> yanwei
		 *<b>Date:</b> 2021年5月29日 下午5:48:23
		 *@param stringDate
		 *@param day
		 *@return
		 *</pre>
		 */
		public static String getStringDateAddDay(String stringDate,long day){
			if(stringDate==null || "".equals(stringDate)){
				return "";
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    Date date=null;
		    String dateString="";
			try {
				date = format.parse(stringDate);
				long time = date.getTime();
				day = day * 24 * 60 * 60 * 1000;
				time += day;
			    dateString = format.format(new Date(time));  
			} catch (ParseException e) {
				e.printStackTrace();
			}  
			return dateString;
		}
		/**
		 * 获取时间戳指定天数的日期
		 * @param longDate
		 * @param day
		 * @return
		 */
		public static String getStringDate(String longDate, Long day){
			if(longDate==null || longDate.equals("")){
				return "";
			}
			if(day==null){
				day=0L;
			}
			Date date = new Date(Long.parseLong(longDate.substring(longDate.indexOf("(")+1, longDate.indexOf(")"))));
			long time=0;
			try {
				time = date.getTime(); // 得到指定日期的毫秒数

				day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数

				time += day; // 相加得到新的毫秒数
				
				date=new Date(time);
			    SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_SHORT);  
			    String dateString = formatter.format(date);
				return dateString;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ""; 

		}
		
		
		
		public static Calendar  getCalendar(){
			Calendar calendar=null;
		    try
		    {
		    	Date date = new Date();
		    	calendar=Calendar.getInstance();
		    	calendar.setTime(date);
		    } catch(Exception e)
		    {
		        e.printStackTrace();
		    }
		    return calendar;
		}
		
		
		/** 
		 * 比较两个日期之间的大小 
		 *  
		 * @param d1 
		 * @param d2 
		 * @return 前者大于等于后者返回true 反之false 
		 */  
		public static boolean compareDate(Date d1, Date d2) {  
		    Calendar c1 = Calendar.getInstance();  
		    Calendar c2 = Calendar.getInstance();  
		    c1.setTime(d1);  
		    c2.setTime(d2);  
		  
		    int result = c1.compareTo(c2);  
		    if (result >= 0)  
		        return true;  
		    else  
		        return false;  
		}
		
		
		
		/** 
		 * 比较两个日期之间的大小 
		 *  
		 * @param d1 
		 * @param d2 
		 * @return 前者大于等于后者返回true 反之false 
		 */  
		public static boolean compareDateString(String d1, String d2) throws ParseException {  
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(d1);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(d2);
		    Calendar c1 = Calendar.getInstance();  
		    Calendar c2 = Calendar.getInstance();  
		    c1.setTime(date1);  
		    c2.setTime(date2);  
		  
		    int result = c1.compareTo(c2);  
		    if (result >= 0)  
		        return true;  
		    else  
		        return false;  
		}
		/**
		 * 
		 *<pre>
		 *<b>Description:</b> 
		 *    比较两个日期之间的大小 ,前者大于后者返回true 反之false 
		 *<b>Author:</b> yanwei
		 *<b>Date:</b> 2021年7月22日 上午11:31:47
		 *@param d1
		 *@param d2
		 *@return
		 *@throws ParseException
		 *</pre>
		 */
		public static boolean compareDateString1(String d1, String d2) throws ParseException {  
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(d1);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(d2);
		    Calendar c1 = Calendar.getInstance();  
		    Calendar c2 = Calendar.getInstance();  
		    c1.setTime(date1);  
		    c2.setTime(date2);  
		  
		    int result = c1.compareTo(c2);  
		    if (result > 0)  
		        return true;  
		    else  
		        return false;  
		}
		
		 
		/**
		 * 
		 *<pre>
		 *<b>Description:</b> 
		 *    获取当前日期前3个月的日期
		 *<b>Author:</b> yanwei
		 *<b>Date:</b> 2021年5月19日 下午2:25:50
		 *@return
		 *</pre>
		 */
		
		public static String getBeforeThree(String strDate){
			String time="";
			try {
				SimpleDateFormat sdf = new SimpleDateFormat (FORMAT_1_STRING);
				Date date =  sdf.parse(strDate);
				sdf = new SimpleDateFormat("yyyy-MM");
				time = sdf.format(date);
				String[] item = time.split("-");
				int year = Integer.parseInt(item[0]);
				int month = Integer.parseInt(item[1]);
				if((month - 3) <= 0){
					month = month + 12 - 3;
					year = year -1;
				}else {
					month = month - 3;
				}
				if(month<10){
					time = year + "-0" + month + "-01";
				}else{
					time = year + "-" + month + "-01";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return time;
		}
		
		/**
		 * 
		 *<pre>
		 *<b>Description:</b> 
		 *    获取当前时间前一天的日期
		 *<b>Author:</b> yanwei
		 *<b>Date:</b> 2020年5月31日 下午5:12:27
		 *@return
		 *</pre>
		 */
		public static String getBeforeTwo(){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1); //得到前一天
			Date date = calendar.getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String format = df.format(date); 
		    return format;
		}
		
		/**
		 * 
		 *<pre>
		 *<b>Description:</b> 
		 *    获取当前时间后一天的日期
		 *<b>Author:</b> yanwei
		 *<b>Date:</b> 2020年5月31日 下午5:12:27
		 *@return
		 *</pre>
		 */
		public static String getAfterDay(){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1); //得到后一天
			Date date = calendar.getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String format = df.format(date); 
		    return format;
		}
		
		
		/**
		 * 
		 *<pre>
		 *<b>Description:</b> 
		 *    获取指定日期后一天的日期
		 *<b>Author:</b> yanwei
		 *<b>Date:</b> 2020年5月31日 下午5:12:27
		 *@return
		 *</pre>
		 */
		public static String getAfterDay(String day){
			String format="";
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date date=df.parse(day);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1); //得到后一天
				date = calendar.getTime();
				format = df.format(date); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		    return format;
		}
		
		/**
		 * 
		 *<pre>
		 *<b>Description:</b> 
		 *    获取当前月份的第一天到当前日期的所有日期
		 *<b>Author:</b> yanwei
		 *<b>Date:</b> 2021年6月9日 上午11:02:31
		 *@return
		 *</pre>
		 */
		public static List<String> getDays(){
			Calendar calendar = Calendar.getInstance();  
		    int maxDate = calendar.get(Calendar.DAY_OF_MONTH);  
			List<String> list=new ArrayList<>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        calendar = Calendar.getInstance();
	        for (int i = maxDate-1; i >=0; i--) {
	        	calendar.setTime(new Date());
	        	calendar.add(Calendar.DATE, - i);
	            Date d = calendar.getTime();
	            String day = format.format(d);
	            list.add(day);
			}
	        return list;
		}
		
		 /**
		  * 
		  *<pre>
		  *<b>Description:</b> 
		  *    获取两个日期之间所有的日期
		  *<b>Author:</b> yanwei
		  *<b>Date:</b> 2021年7月30日 下午6:18:08
		  *@param startTime
		  *@param endTime
		  *@return
		  *</pre>
		  */
		 public static List<String> getDays(String startTime, String endTime) {

		        // 返回的日期集合
		        List<String> days = new ArrayList<String>();

		        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        try {
		            Date start = dateFormat.parse(startTime);
		            Date end = dateFormat.parse(endTime);

		            Calendar tempStart = Calendar.getInstance();
		            tempStart.setTime(start);

		            Calendar tempEnd = Calendar.getInstance();
		            tempEnd.setTime(end);
		            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
		            while (tempStart.before(tempEnd)) {
		                days.add(dateFormat.format(tempStart.getTime()));
		                tempStart.add(Calendar.DAY_OF_YEAR, 1);
		            }

		        } catch (ParseException e) {
		            e.printStackTrace();
		        }

		        return days;
		    }
		

		/**
		 * 
		 *<pre>
		 *<b>Description:</b> 
		 *    将日期转成时间戳
		 *<b>Author:</b> yanwei
		 *<b>Date:</b> 2021年7月22日 上午11:20:00
		 *@param dateStr
		 *@param format
		 *@return
		 *</pre>
		 */
		 public static long  DateToTimeStamp(String dateStr, String format) {
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat(format);
	            Date date = sdf.parse(dateStr);
	            return date.getTime();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return 0;
		 }
		 
}
