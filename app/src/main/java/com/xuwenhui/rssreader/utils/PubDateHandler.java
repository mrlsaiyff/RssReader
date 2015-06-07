package com.xuwenhui.rssreader.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 对pubDate时间格式的处理
 * Created by xwh on 2015/6/4.
 */
public class PubDateHandler {

    //将GMT时间转化成本地时间
    public static String GMTToLocale(String GMTString) {
        DateFormat dateFormatSource = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormatSource.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = null;
        try {
            date = dateFormatSource.parse(GMTString);
        } catch (ParseException e) {
            Log.d("TAG", "时间转换出错");
        }
        DateFormat dateFormatTarget = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        dateFormatTarget.setTimeZone(TimeZone.getDefault());
        return dateFormatTarget.format(date);
    }
}
