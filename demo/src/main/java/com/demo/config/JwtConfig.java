package com.demo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Author cc
 * @Date 2020/6/28 17:39
 * @Version 1.0
 * JWT配置
 */
@Component
@Data
public class JwtConfig {
    private String secret="iwqjhda8232bjgh432[cicada-smile]";
    //2小时过期(毫秒)
    private long expire=7200000;
    private String header="token";

    /*
     * 根据身份ID标识，生成Token
     */
    public String getToken (Map<String,Object> map){
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(map)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    /*
     * 获取 Token 中注册信息
     */
    public Map<String,Object> getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /*
     * Token 是否过期验证
     */
    public boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }

}
