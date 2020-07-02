package com.demo.controller;

import com.demo.config.JwtConfig;
import com.demo.constants.Response;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author cc
 * @Date 2020/6/28 17:43
 * @Version 1.0
 */
@RestController
public class TokenController {
    @Resource
    private JwtConfig jwtConfig ;
    // 拦截器直接放行，返回Token
    @PostMapping("/login")
    public Response login (@RequestParam("userName") String userName,
                           @RequestParam("passWord") String passWord){
        Map<String,String> result = new HashMap<>() ;
        // 省略数据源校验
        Map<String,Object> map = new HashMap<>();
        map.put("userName",userName);
        map.put("passWord",passWord);
        String token = jwtConfig.getToken(map) ;
        if (!StringUtils.isEmpty(token)) {
            result.put("token",token) ;
        }
        result.put("userName",userName);
        return new Response(result);
    }
    // 需要 Token 验证的接口
    @PostMapping("/login1/info")
    public String info (){
        int i=1/0;
        return "info" ;
    }
}
