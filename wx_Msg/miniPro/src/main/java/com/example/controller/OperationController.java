package com.example.controller;

import com.alibaba.fastjson.JSONObject;

import com.example.utils.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @Decription 具体操作
 * @Author wxm
 * @Date 2019/3/4 11:18
 **/
@Controller
@EnableScheduling
public class OperationController {
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;

       /**
     * 第一步：获取AccessToken;
     * @return
     */
    @RequestMapping("/getAccessToken")
    @ResponseBody
    public  String  getAccessToken(){
        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
        // 发送请求，返回Json字符串
        String str = HttpRequest.httpReq(url, "GET", null);
        // 转成Json对象 获取openid
        JSONObject jsonObject = JSONObject.parseObject(str);
        System.out.println("access_token---"+jsonObject.toJSONString());
        System.out.println("expires_in---"+Integer.valueOf(jsonObject.get("expires_in").toString()));
        // 我们需要的openid，在一个小程序中，openid是唯一的
        String access_token = jsonObject.get("access_token").toString();
        return access_token;
    }

    @Scheduled(cron="0 0/1 * * * * ")//代表每一分钟执行一次   必须在类上加上@EnableScheduling 否则不会定时执行
    public  void start() {
        System.out.println(new Date());
    }


}
