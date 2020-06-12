package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author cc
 * @Date 2020/6/12 13:51
 * @Version 1.0
 */
@Data
public class SubscribeMessageVO {
    private Thing1 thing1;
    private Date2 date2;
    private Time3 time3;
    private Thing4 thing4;

    @Data
    @AllArgsConstructor
    public static class Thing4{
        private String value;
    }
    @Data
    @AllArgsConstructor
    public static class Thing1{
        private String value;
    }
    @Data
    @AllArgsConstructor
    public static class Date2{
        private String value;
    }
    @Data
    @AllArgsConstructor
    public static class Time3{
        private String value;
    }

}
