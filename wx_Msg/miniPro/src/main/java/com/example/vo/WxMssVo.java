package com.example.vo;

/**
 * @Author cc
 * @Date 2020/6/11 17:40
 * @Version 1.0
 * 小程序推送所需数据
 */
import lombok.Data;
import java.util.Map;


@Data
public class WxMssVo {
    private String touser;//用户openid
    private String template_id;//模版id
    private String page = "index";//默认跳到小程序首页
    //    private String emphasis_keyword = "keyword1.DATA";//放大那个推送字段
    private SubscribeMessageVO data;//推送文字
}

