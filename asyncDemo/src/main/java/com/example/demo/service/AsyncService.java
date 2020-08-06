package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.Future;

/**
 * @Author cc
 * @Date 2020/7/28 10:56
 * @Version 1.0
 */
@Service
public class AsyncService {
    Logger log = LoggerFactory.getLogger(AsyncService.class);

    // 发送提醒短信 1
    @Async("taskExecutor")// 指定线程池，也可以直接写@Async
    public void sendMessage1() throws InterruptedException {
        log.info("发送短信方法---- 1   执行开始");
        Thread.sleep(5000); // 模拟耗时
        log.info("发送短信方法---- 1   执行结束" + new Date());
    }

    // 发送提醒短信 2
    @Async("taskExecutor") // 指定线程池，也可以直接写@Async
    public void sendMessage2() throws InterruptedException {

        log.info("发送短信方法---- 2   执行开始");
        Thread.sleep(2000); // 模拟耗时
        log.info("发送短信方法---- 2   执行结束" + new Date());
    }

    // 发送提醒短信 3
    @Async("taskExecutor") // 指定线程池，也可以直接写@Async
    public Future<String> sendMessage3() throws InterruptedException {

        log.info("发送短信方法---- 3   执行开始"+ new Date());
        Thread.sleep(2000); // 模拟耗时
        log.info("发送短信方法---- 3   执行结束" + new Date());
        return new AsyncResult<String>("发送短信3");
    }

    // 发送提醒短信 3
    @Async("taskExecutor") // 指定线程池，也可以直接写@Async
    public Future<String> sendMessage4() throws InterruptedException {

        log.info("发送短信方法---- 4   执行开始"+ new Date());
        Thread.sleep(2000); // 模拟耗时
        log.info("发送短信方法---- 4   执行结束" + new Date());
        return new AsyncResult<String>("发送短信4");
    }
}
