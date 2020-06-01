package com.cc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by kaimin on 12/7/2018.
 * time : 09:30
 */
@Configuration
public class RedisConfig {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

  @Bean("redisTemplate")
  public RedisTemplate<String,Object> redisTemplate(){
      RedisTemplate<String,Object> redisTemplate =new RedisTemplate<String, Object>();
//      设置数据存入redis的序列化方式，并开启事务
      redisTemplate.setKeySerializer(new StringRedisSerializer());
      /*redisTemplate.setHashKeySerializer(new StringRedisSerializer());
      redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());*/
      redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
      // 开启事务
      redisTemplate.setEnableTransactionSupport(true);
      redisTemplate.setConnectionFactory(redisConnectionFactory);
      return redisTemplate;
  }

  @Bean("redisUtil")
  public RedisUtil redisUtil(RedisTemplate<String,Object> redisTemplate){
      RedisUtil redisUtil=new RedisUtil();
      redisUtil.setRedisTemplate(redisTemplate);
      return redisUtil;
  }



}
