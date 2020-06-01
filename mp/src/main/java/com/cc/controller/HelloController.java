package com.cc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class HelloController {
    /**
     * 前往thymeleaf模板引擎渲染的主页,模板页面放在resources/templates下
     * 请求地址为http://localhost:8090/
     */
    @GetMapping("/hello")
    public String hello(Model model){
        //设置两个参数传入到模板引擎
        //第一个参数是键 第二个参数是值
        model.addAttribute("str","字符串");
        model.addAttribute("bool",false);

        model.addAttribute("list", Arrays.asList(1,2,3,4,5));
        //指定模板为templates文件夹下的index.html
        return "Hello";
    }

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }

}
