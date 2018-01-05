package com.suke.czx.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期处理
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2017年12月21日 下午12:53:33
 */
public class DateUtils {
    /** 时间格式(yyyy-MM-dd) */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    public final static String DATE_PATTERN_C = "yyyy年MM月dd日";
    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 判断两个时间是否正常
     * @param start 开始时间
     * @param end 结束时间
     * @return  1:正常  2：结束时间大于开始时间 3：两个时间一样
     */
    public static int compareDate(String start, String end) {
        DateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            Date dt1 = df.parse(start);
            Date dt2 = df.parse(end);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    public static Date getNextMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE,60);
        date = calendar.getTime();
        return date;
    }

    public static String getDateUUID(){
        Calendar Cld = Calendar.getInstance();
        int YY = Cld.get(Calendar.YEAR) ;
        int MM = Cld.get(Calendar.MONTH)+1;
        int DD = Cld.get(Calendar.DATE);
        int HH = Cld.get(Calendar.HOUR_OF_DAY);
        int mm = Cld.get(Calendar.MINUTE);
        int SS = Cld.get(Calendar.SECOND);
        int MI = Cld.get(Calendar.MILLISECOND);
        int number = (int)(Math.random()*999);
        return YY + "" + MM + "" + DD + "" + HH + "" + mm + "" + SS + "" + MI + "" + number;
    }


    public static String stringToDate(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy K:m:s a", Locale.ENGLISH);
        Date d2 = null;
        try {
            d2 = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return format(d2,DATE_TIME_PATTERN);
    }

    public static Date timeToDate(Long time){
        SimpleDateFormat format =  new SimpleDateFormat(DATE_TIME_PATTERN);
        String d = format.format(time);
        try {
            Date date=format.parse(d);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date strToDate(String str){
        SimpleDateFormat format =  new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            String d = format.format(DateFormat.getDateInstance().parse(str));
            Date date=format.parse(d);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
