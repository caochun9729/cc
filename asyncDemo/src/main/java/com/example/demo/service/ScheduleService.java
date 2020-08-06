package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author cc
 * @Date 2020/7/28 10:47
 * @Version 1.0
 * 线程池配合定时任务
 */
@Service
public class ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 定时任务 2秒执行一次
     */
    private static final String times1 = "* 0/10 * * * ?";
    /**
     * 定时任务方法1
     */
    @Async // 异步执行，线程之间不会互相干扰，任务自动提交到线程池
    @PostConstruct // 加上该注解项目启动时就执行一次该方法
    @Scheduled(cron=times1)
    public void taskTest() {

        logger.info("定时任务开始执行。。。"+sdf.format(new Date()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sdf.format(new Date())+"执行定时任务1执行");
        logger.info("定时任务执行结束。。。");
    }

}
