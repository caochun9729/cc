package com.cc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysLog implements Serializable {
    private String id; //主键

    private String username; //用户名

    private String operation; //操作

    private String method; //方法名

    private String params; //参数

    private String ip; //ip地址

    private Date createDate; //操作时间

    private String url; //操作的url

    private String exceptionName;//异常名称

    private String exceptionMsg;//异常信息
}
