package com.example.jing.kapep.Helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jing on 2017/6/15.
 */
// 字符串的封装，转化，装箱放在这里面
public class KapStringChangeHelper {
    /**
     * 对外开放的转化
     * */
    public static String MessageTimeIntervalToTimeString(long timeInval){
        return TimeIntervalToTimeString(timeInval,dayDateFormatString);
    }
    public static long MessageTimeStringToTimeInterval(String timeString) {
        return TimeStringToTimeInterval(timeString,dayDateFormatString);
    }


    /**
     * 时间转化相关
     * */
    private static final String dayDateFormatString = "yyyy/MM/dd HH:mm";
    // long to time string
    private static String TimeIntervalToTimeString(long timeInval,String dayDateFormatString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dayDateFormatString);
        String dateString = simpleDateFormat.format(new Date(timeInval));
        return dateString;
    }
    // time string to long
    private static long TimeStringToTimeInterval(String timeString,String dayDateFormatString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dayDateFormatString);
        long interval = -1;
        try {
            Date date = simpleDateFormat.parse(timeString);
            if (date != null )interval = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }


}
