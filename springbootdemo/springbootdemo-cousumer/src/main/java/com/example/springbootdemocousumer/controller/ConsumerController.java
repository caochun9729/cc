package com.example.springbootdemocousumer.controller;

import com.example.springbootdemocousumer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author cc
 * @Date 2020/10/30 14:45
 * @Version 1.0
 */
@RestController
public class ConsumerController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "getConsumer")
    public String getConsumer(@RequestParam String id){
        String str =  productService.getProduct(id);
        return str;
    }

}
