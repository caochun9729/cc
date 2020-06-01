package com.cc.constant;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:统一返回结果类
 * @author: caochun
 */

@Data
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -4505655308965878999L;

    //请求成功返回码为：1
    private static final Integer successCode = 200;
    //请求成功返回码为：1
    private static final Integer errorCode = 500;
    //返回数据
    private T data;
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
    public Response(Integer code, String message, T data){
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Response(T data){
        this();
        this.data = data;
    }

    public static Response success(Object o){
        return new Response(o);
    }

    public static Response fail(String o){
        return new Response(errorCode,o);
    }
}
