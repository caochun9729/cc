package com.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final SimpleDateFormat ymdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s){
        String res = "";
        if (!"".equals(s)) {
            try {
                res = String.valueOf(ymdHms.parse(s).getTime());
            } catch (Exception e) {
                System.out.println("传入了null值");
            }
        }else {
            long time = System.currentTimeMillis();
            res = String.valueOf(time);
        }
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static Date stampToDate(Long time) throws ParseException {
        String times = ymdHms.format(new Date(time));
        return ymdHms.parse(times);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(dateToStamp("2020-06-29 00:00:00"));
        System.out.println(stampToDate(1593397785000L));
    }
}
