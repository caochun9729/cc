package com.example.demo.utils;

import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.UserInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author cc
 * @Date 2020/7/16 13:58
 * @Version 1.0
 * 2层for循环优化
 */
public class ForEachUtils {
    public static void main(String[] args) {
        /* 用户信息集合 */
        List<UserInfo> userInfoList = new ArrayList<>();
        /* 订单信息集合 */
        List<OrderInfo> orderInfoList = new ArrayList<>();
        /* 手动向集合中添加十万条数据 */
        for (int i = 0; i < 100000; i++) {
            String userId = i + "";
            String name = "用户" + i;
            String gender = "female";
            UserInfo userInfo = new UserInfo(userId, name, gender);
            Long orderId = Long.valueOf(createNum(6));
            Long skuId = Long.valueOf(createNum(8));
            OrderInfo orderInfo = new OrderInfo(userId, orderId, skuId, new BigDecimal("66"));
            userInfoList.add(userInfo);
            orderInfoList.add(orderInfo);
        }

        long start = System.currentTimeMillis();
        //采用原始的双层for循环查询累计耗时：200005毫秒
        //test1(userInfoList,orderInfoList);
        //采用remove的双层for循环查询累计耗时：96088毫秒
        //test2(userInfoList,orderInfoList);
        //采用hashmap的双层for循环查询累计耗时：68毫秒
        test3(userInfoList,orderInfoList);
        long end = System.currentTimeMillis();
        System.out.println("双层for循环查询累计耗时：" + (end - start) + "毫秒");
    }

    /**
     * 原始方法
     * @param userInfoList
     * @param orderInfoList
     */
    public static void test1(List<UserInfo> userInfoList,List<OrderInfo> orderInfoList){
        for (UserInfo userInfo : userInfoList) {
            int count=0;
            for (OrderInfo orderInfo : orderInfoList) {
                if (userInfo.getUserId().equals(orderInfo.getUserId())) {
                    count++;
                }
            }
            userInfo.setCount(count);
        }
    }

    /**
     * 原始方法
     * @param userInfoList
     * @param orderInfoList
     */
    public static void test2(List<UserInfo> userInfoList,List<OrderInfo> orderInfoList){
        userInfoList.stream().forEach(user ->{
            int count=0;
            for(int m=0;m<orderInfoList.size();m++){
                if(user.getUserId().equals(orderInfoList.get(m).getUserId())){
                    count++;
                    orderInfoList.remove(m);
                    m--;
                }
            }
            user.setCount(count);
        });

    }


    public static void test3(List<UserInfo> userInfoList,List<OrderInfo> orderInfoList){
        List<UserInfo> list = new ArrayList<UserInfo>();
        final Map<String, UserInfo> gradeInfoMap = userInfoList.stream()
                .collect(
                        Collectors.toMap(UserInfo::getUserId, Function.identity()));

        list = orderInfoList.stream().map(h -> {
            UserInfo userInfo = gradeInfoMap.get(h.getUserId());
            int count = userInfo.getCount();
            count++;
            userInfo.setCount(count);
            return userInfo;
        }).collect(Collectors.toList());
        System.out.println(list.get(10000).getCount());
    }

    /**
     * 生成指定长度的随机数
     *
     * @param length
     * @return
     */
    public static String createNum(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

}
