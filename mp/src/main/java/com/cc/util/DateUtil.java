package com.cc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String res = "";
        if (!"".equals(s)) {
            try {
                res = String.valueOf(sdf.parse(s).getTime());
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
    public static String stampToDate(Long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times = format.format(new Date(time));
        return times;
    }

    public static Date stampToDa(Long time){
        return new Date(time);
    }

    public static void main(String[] args) {
        System.out.println(stampToDa(11111111111111l));
    }
}
