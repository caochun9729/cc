package com.cc.runner;

import com.cc.controller.SocketController;
import com.cc.redis.RedisUtil;
import com.cc.schedule.TestTask;
import com.cc.service.IRemindRecordService;
import com.cc.websocket.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IRemindRecordService remindRecordService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("项目启动后执行...");

        String[] msg={"消息1","消息2"};
        String[] msg1={"消息3","消息4"};
        List<MessageResponse> messageResponseList=new ArrayList<>();
        messageResponseList.add(new MessageResponse("cc", "消息1",null));
        messageResponseList.add(new MessageResponse("dd", "消息2",null));
        messageResponseList.add(new MessageResponse("dd", "消息3",null));
        //redisUtil.del("allMessage");
        //redisUtil.lSet("allMessage", Collections.singletonList(messageResponseList));


        //消息初始化
        /*List<Map<String, Object>> mapList = remindRecordService.remindRecordInit();
        redisUtil.delLike("remind");
        redisUtil.set("remind:init", mapList);
        //解析成单个用户
        remindRecordService.userRemindRecordInit(mapList);
        System.out.println("解析成单个用户");*/

    }
}
