package com.demo.interceptor;
import com.demo.config.JwtConfig;
import com.demo.constants.Constants;
import com.demo.util.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author cc
 * @Date 2020/6/28 17:41
 * @Version 1.0
 * Token拦截器
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private JwtConfig jwtConfig ;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 地址过滤
        String uri = request.getRequestURI() ;
        if (!uri.contains("/login1")){
            return true ;
        }
        // Token 验证
        String token = request.getHeader(jwtConfig.getHeader());
        if(StringUtils.isEmpty(token)){
            token = request.getParameter(jwtConfig.getHeader());
        }
        if(StringUtils.isEmpty(token)){
            throw new Exception(jwtConfig.getHeader()+ "不能为空");
        }
        Map<String,Object> claims = jwtConfig.getTokenClaim(token);
        if(claims == null || jwtConfig.isTokenExpired(DateUtil.stampToDate(Long.parseLong(claims.get(Constants.exp).toString()) * Constants.ms))){
            throw new Exception(jwtConfig.getHeader() + "失效，请重新登录");
        }

        String userName = request.getHeader(Constants.USER_NAME);
        if(StringUtils.isEmpty(userName)){
            throw new Exception("请求头userName不能为空");
        }
        if(!userName.equals(claims.get(Constants.USER_NAME))){
            throw new Exception("token不正确");
        }
        return true;
    }
}
