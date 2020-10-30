package com.example.springbootdemocousumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author cc
 * @Date 2020/10/30 14:47
 * @Version 1.0
 */
//name 为product项目中application.yml配置文件中的application.name;
//path 为product项目中application.yml配置文件中的context.path;
@FeignClient(name = "product-server",path ="/product" )
//@Componet注解最好加上，不加idea会显示有错误，但是不影响系统运行；
@Component
public interface ProductService {
    @GetMapping(value = "getProduct")
    String getProduct(@RequestParam String id);
}
