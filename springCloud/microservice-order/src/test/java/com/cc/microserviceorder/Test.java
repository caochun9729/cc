package com.cc.microserviceorder;

import com.alibaba.fastjson.JSON;
import com.cc.microserviceorder.entity.Item;
import com.cc.microserviceorder.entity.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicroserviceOrderApplication.class)
public class Test {
    @Autowired
    private RestTemplate restTemplate;


    @org.junit.Test
    public void test1(){
        Item item=new Item(2121l,"title","pic","desc",21l);
        System.out.println(restTemplate.postForObject("http://app-item/postItem",item,Item.class));

        Map<String,Object> map=new HashMap<>();
        map.put("id",1);
        map.put("name","tom");
        System.out.println(restTemplate.getForObject("http://app-item/getItem?id={id}&name={name}", Item.class, map));
    }
}
