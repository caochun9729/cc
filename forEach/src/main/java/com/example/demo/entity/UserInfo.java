package com.example.demo.entity;

import lombok.Data;

/**
 * @author cc
 * @date 2020/7/16 13:56
 * @Version 1.0
 * 用户信息类
 */
@Data
public class UserInfo {
    /**
     * 用户Id
     */
    private String userId;

    /**
     *  用户名
     */
    private String name;

    /**
     * 用户性别
     */
    private String gender;

    private int count;

    public UserInfo(){

    }

    public UserInfo(String userId, String name, String gender) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
    }
}


