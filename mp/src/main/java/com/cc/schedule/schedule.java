package com.cc.schedule;

import com.cc.websocket.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class schedule {
    @Autowired
    private SocketHandler socketHandler;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //每隔5秒执行一次
    @Scheduled(cron = "0 0/5 * * * *")
    public void testTasks() {
        socketHandler.sendMessageToUsers(new TextMessage("定时任务执行时间：" + dateFormat.format(new Date())));
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
    }

    //每天3：05执行
    @Scheduled(cron = "0 05 03 ? * *")
    public void testTask() {
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
    }
}
