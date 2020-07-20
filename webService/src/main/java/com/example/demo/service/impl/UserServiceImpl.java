package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author cc
 * @Date 2020/7/20 10:31
 * @Version 1.0
 */
@WebService(serviceName="UserService",//对外发布的服务名
        targetNamespace="http://service.demo.example.com/",//指定你想要的名称空间，通常使用使用包名反转
        endpointInterface="com.example.demo.service.UserService")//服务接口全路径, 指定做SEI（Service EndPoint Interface）服务端点接口
@Service
public class UserServiceImpl implements UserService {
    private Map<String, User> userMap = new HashMap<String, User>(4);
    public UserServiceImpl() {
        System.out.println("向实体类插入数据");
        User user = new User();
        user.setUserId("1");
        user.setUserName("test1");
        user.setEmail("maplefix@163.xom");
        userMap.put(user.getUserId(), user);

        user = new User();
        user.setUserId("2");
        user.setUserName("test2");
        user.setEmail("maplefix@163.xom");
        userMap.put(user.getUserId(), user);

        user = new User();
        user.setUserId("3");
        user.setUserName("test3");
        user.setEmail("maplefix@163.xom");
        userMap.put(user.getUserId(), user);
    }

    @Override
    public String getUserName(String userId) {
        return "userId为：" + userId;
    }

    @Override
    public User getUserObject(User user) {
        System.out.println("userMap是:"+userMap);
        return userMap.get(user.getUserId());
    }

    @Override
    public User getUser(String userId) {
        System.out.println("userMap是:"+userMap);
        return userMap.get(userId);
    }

}
