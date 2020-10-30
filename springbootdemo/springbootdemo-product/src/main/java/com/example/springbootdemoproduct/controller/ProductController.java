package com.example.springbootdemoproduct.controller;

import com.example.springbootdemoentity.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cc
 * @Date 2020/10/30 11:41
 * @Version 1.0
 */
@RestController
public class ProductController {
    @GetMapping("/getProduct")
    public String getProduct(@RequestParam String id){
        Product p=new Product();
        p.setName(id);
        return p.toString();
    }
}
