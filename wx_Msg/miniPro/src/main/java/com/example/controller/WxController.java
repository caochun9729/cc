package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.utils.HttpClientUtil;
import com.example.utils.HttpRequest;
import com.example.utils.JsonUtils;
import com.example.vo.SubscribeMessageVO;
import com.example.vo.WxMssVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cc
 * @Date 2020/6/12 11:26
 * @Version 1.0
 */
@RestController
@RequestMapping("/wx")
public class WxController {

    /**
     * 获取微信小程序的openid
     * @param code
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getOpenid", method = RequestMethod.GET)
    public Map getOpenid(String code ) throws Exception{

        Map<String,Object> map = new HashMap<String,Object>();
        //code = "081ZExyD0qnP4j2LV5yD0hFLyD0ZExyK";
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            System.out.println("map1:" + map);
            return map;
        }
        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wx06b03d31fcc8d335";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "26f846f670324348a2e6d189bc45e4c0";
        //授权（必填）
        String grant_type = "authorization_code";
        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        System.out.println("openid:" + openid);
        map.put("opneId",openid);
        return map;
    }


    public void sendMessage() {
        SubscribeMessageVO bean = new SubscribeMessageVO();
        bean.setThing4(new SubscribeMessageVO.Thing4("活动要开始了啊"));
        bean.setThing1(new SubscribeMessageVO.Thing1("not2s"));
        bean.setDate2(new SubscribeMessageVO.Date2("2019-11-11 10:10:00"));
        bean.setTime3(new SubscribeMessageVO.Time3("2019-11-18 10:00:00"));
        WxMssVo wxMssVo=new WxMssVo();
        wxMssVo.setTouser("ox0jd4t5RDgNqYJ1uc2u94HSSGr0");
        wxMssVo.setTemplate_id("5U_bsCSivBtb52zCpup4tBXrZi7G4aQ9Hh73Mt2iGFk");//模板id号码
        wxMssVo.setData(bean);
        push(wxMssVo);
    }
    public void push(WxMssVo wxMssVO) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + "34_InKG_iK3bq3LOLgiKwy9cKXtiLaGyeR7mrbopVDYZ64oPDpdsbo9sblAR8Zm7SG5OQLue0vikmFZmc4GqaZJtDsx-AKCl8-j9xhXJaToxKBb1Pt2BIXCjsvsbKuxEioBuanOt87JgFbnr1o3ZGFaAJAQXW";
        String json = JsonUtils.objectToJson(wxMssVO);
        String vxResult = HttpClientUtil.doPostJson(url, json);
        System.out.println("返回的内容：" + vxResult);
    }


    public static void main(String[] args) {
        //发送服务通知消息
        WxController w=new WxController();
        w.sendMessage();
    }
}
