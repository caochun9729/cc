package com.cc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.constant.Response;
import com.cc.entity.SysUser;
import com.cc.redis.RedisUtil;
import com.cc.service.SysUserService;
import com.cc.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/redis")
@Api(value = "redis", description = "redis")
public class RedisController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @GetMapping("redisAddAndGet")
    @ApiOperation(value = "redis增加查询测试")
    @ApiImplicitParam(name = "name", value = "姓名", dataType = "String", paramType = "query")
    public Object redisAddAndGet(@RequestParam String name) {
        redisUtil.set("name", name,  1000L);
        Object n = redisUtil.get("name");
        logger.info("mykey:" + redisUtil.get("mykey").toString());
        return n;
    }


    public static void main(String[] args) {
        // 获取当前时间戳
        System.out.println(DateUtil.stampToDate(1524179718000L));
    }

    @GetMapping("getUsersForRedis")
    @ApiOperation(value = "缓存用户列表")
    @Cacheable(value = "CcRedis", key = "'users'")
    // @Cacheable(cacheNames= {"users"},unless="#result == null")
    // 当重复使用相同参数调用方法的时候，方法本身不会被调用执行，即方法本身被略过了，取而代之的是方法的结果直接从缓存中找到并返回
    public List<SysUser> getUsersForRedis() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("del_flag", 0);
        List<SysUser> list = sysUserService.list(queryWrapper);
        return list;
    }

    @RequestMapping("/deleteUser")
    @CacheEvict(value = "CcRedis", key = "'users'")
    //@CacheEvict删除指定缓存，@Cacheable从缓存中读取数据，如存在不执行方法
    public Response deleteUser() {
        return new Response();
    }

    @RequestMapping("/getValueByKey")
    public Object getValueByKey() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("aaaaaaaaaaaaaa");
        sysUser.setUpdateTime(new Date());
        redisUtil.set("user", sysUser);
        Object users = redisUtil.get("user");
        return users;
    }
}
