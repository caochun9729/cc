package com.cc.util;

import cn.hutool.core.date.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author cc
 * @Date 2020/6/1 9:42
 * @Version 1.0
 */
public class HolidayTest {

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 节假日列表
     */
    private static List<Calendar> holidayList = new ArrayList<Calendar>();


    public static void main(String[] args) {
        try {
            Calendar ca = Calendar.getInstance();
            Date d = df.parse("2020-06-01");
            //设置当前时间
            ca.setTime(d);

            //初始节假日
            initHolidayList("2020-06-02");
            initHolidayList("2020-11-26");
            initHolidayList("2020-11-27");

            Calendar c = addDateByWorkDay(ca,5);
            System.out.println(df.format(c.getTime()));

        } catch ( Exception e) {
            // TODO: handle exception
            System.out.println(e.getClass());
            e.printStackTrace();
        }

    }



    /**
     *
     * <p>Title: addDateByWorkDay </P>
     * <p>Description: TODO  计算相加day天，并且排除节假日和周末后的日期</P>
     * @param calendar  当前的日期
     * @param day  相加天数
     * @return
     * return Calendar    返回类型   返回相加day天，并且排除节假日和周末后的日期
     * throws
     * date 2014-11-24 上午10:32:55
     */
    public static Calendar addDateByWorkDay(Calendar calendar,int day){

        try {
            for (int i = 0; i < day; i++) {

                calendar.add(Calendar.DAY_OF_MONTH, 1);

                if(checkHoliday(calendar)){
                    i--;
                }
            }
            return calendar;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     *
     * <p>Title: checkHoliday </P>
     * <p>Description: TODO 验证日期是否是节假日</P>
     * @param calendar  传入需要验证的日期
     * @return
     * return boolean    返回类型  返回true是节假日，返回false不是节假日
     * throws
     * date 2014-11-24 上午10:13:07
     */
    public static boolean checkHoliday(Calendar calendar) throws Exception{

        //判断日期是否是周六周日
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            return true;
        }
        //判断日期是否是节假日
        for (Calendar ca : holidayList) {
            if(ca.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    ca.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)&&
                    ca.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)){
                return true;
            }
        }

        return false;
    }

    /**
     *
     * <p>Title: initHolidayList </P>
     * <p>Description: TODO  把所有节假日放入list</P>
     * @param date  从数据库查 查出来的格式2014-05-09
     * return void    返回类型
     * throws
     * date 2014-11-24 上午10:11:35
     */
    public static void initHolidayList( String date){

        String [] da = date.split("-");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(da[0]));
        //月份比正常小1,0代表一月
        calendar.set(Calendar.MONTH, Integer.valueOf(da[1])-1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(da[2]));
        holidayList.add(calendar);
    }



}
