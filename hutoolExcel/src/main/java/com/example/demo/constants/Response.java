package com.example.demo.constants;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:统一返回结果类
 * @author: cc
 */

@Data
public class Response implements Serializable {
    private static final long serialVersionUID = -4505655308965878999L;

    //请求成功返回码为：1
    private static final Integer successCode = 200;
    //返回数据
    private Object data;
    //返回码
    private Integer code;
    //返回描述
    private String message;

    public Response(){
        this.code = successCode;
        this.message = "请求成功";
    }

    public Response(Integer code, String message){
        this();
        this.code = code;
        this.message = message;
    }
    public Response(Integer code, String message, Object data){
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Response(Object data){
        this();
        this.data = data;
    }

    /**
     * code:200,500 Message:错误返回错误信息 data:正确返回对象
     */

    public static Response success(Object o) {
        return new Response(o);
    }

    /**
     * @param errorMessage 错误信息
     * @Description://
     * @Return:
     * @Author: wzw
     * @Date: 2019/11/25 18:09
     */
    public static Response error(String errorMessage) {
        return new Response(500, errorMessage);
    }
}
