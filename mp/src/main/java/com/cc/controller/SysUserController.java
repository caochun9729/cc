package com.cc.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.aspect.MyLog;
import com.cc.constant.Response;
import com.cc.entity.SysUser;
import com.cc.redis.RedisUtil;
import com.cc.service.SysUserService;
import com.cc.util.DateUtil;
import com.cc.util.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.convert.EntityWriter;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.jdbc.datasource.WebSphereDataSourceAdapter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cc
 * @since 2019-11-12
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理",description = "用户管理")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    private static final Logger logger= LoggerFactory.getLogger(SysUserController.class);

    @GetMapping("/getUsers")
    public Object getUsers(){
        logger.info("获取全部用户");
        logger.error("hello");
        Map<String,Object> map=new HashMap<>();
        map.put("del_flag",0);
        Collection<SysUser> sysUsers = sysUserService.listByMap(map);
        return sysUsers;
    }

    /*测试操作日志保存到数据库*/
    /*假装登录，将用户信息存到session*/
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    @ApiImplicitParam(name = "userName",value = "用户名",dataType = "String",paramType = "query")
    public Response login(@RequestBody SysUser sysUser){
        QueryWrapper<SysUser> sysUserQueryWrapper=new QueryWrapper<>();
        sysUserQueryWrapper.eq("USERNAME",sysUser.getUsername()).eq("PASSWORD", MD5.crypt(sysUser.getPassword()));
        List<SysUser> sysUsers = sysUser.selectList(sysUserQueryWrapper);
        if(!CollectionUtils.isEmpty(sysUsers)){
            return new Response();
        }
        return new Response(500,"账号或密码错误");
    }

    /*记录日志*/
    @MyLog(operation = "查询用户信息")
    @ApiOperation(value = "查询用户信息")
    @ApiImplicitParam(name = "sysUser",value = "SysUser对象",dataType = "SysUser")
    @GetMapping("/log")
    public Response insertLog(@RequestBody SysUser sysUser){
        return new Response();
    }

    /**
     * 异常处理
     */
    @GetMapping("/test")
    public void test(){
        List<SysUser> sysUsers=new ArrayList<>();
        System.out.println(sysUsers.get(0).getUsername());
    }
}

