package com.example.demo.controller;

import com.example.demo.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author cc
 * @Date 2020/7/28 11:11
 * @Version 1.0
 */
@RestController
public class AsyncController {


    @Autowired
    private AsyncService asyncService;

    @GetMapping("/a")
    public String a() throws InterruptedException {
        asyncService.sendMessage1();
        asyncService.sendMessage2();
        return "hello world";
    }

    @GetMapping("/b")
    public String b() throws InterruptedException, ExecutionException {
        Future<String> stringFuture = asyncService.sendMessage3();
        if(stringFuture.isDone()){
            System.out.println(stringFuture.get());
            return stringFuture.get();
        }
        return "hello world!";
    }
}
