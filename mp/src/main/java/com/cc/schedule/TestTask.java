package com.cc.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @Author cc
 * @Date 2020/4/23 17:29
 * @Version 1.0
 */
@RestController
public class TestTask {
    private static Logger logger = LoggerFactory.getLogger(TestTask.class);

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public static Map<String, ScheduledFuture<?>> taskmap = new HashMap<>();


    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @RequestMapping("/start")
    public String start() {
        TaskThread tt = new TaskThread("222");
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(tt, new CronTrigger("0/30 * * * * *"));
        taskmap.put("task1", future);
        System.out.println("开始");
        return "start";
    }

    @RequestMapping("/stop")
    public String stop() {
        ScheduledFuture<?> future = taskmap.get("task1");
        if (future != null) {
            future.cancel(true);
        }
        System.out.println("停止");
        return "stop";
    }

    @RequestMapping("/change")
    public String change() {

        stop();// 先停止，在开启.
        ScheduledFuture<?> future = taskmap.get("task1");
        TaskThread tt = new TaskThread("222");
        future = threadPoolTaskScheduler.schedule(tt, new CronTrigger("*/5 * * * * *"));
        System.out.println("DynamicTask.startCron10()");
        logger.info("开始..."+ LocalDateTime.now().toLocalTime());
        return "changeCron10";
    }

    @RequestMapping("/change1")
    public void change1() {
        CronTrigger trigger = new CronTrigger("0/3 * * * * ?");
    }

    //内部类
    public class TaskThread implements Runnable {

        private String id;

        public TaskThread(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            //根据传来的参数执行要定时的任务
            logger.info(id + new Date());
        }

    }
}
