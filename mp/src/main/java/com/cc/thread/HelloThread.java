package com.cc.thread;

import com.cc.websocket.SocketHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class HelloThread {
    @Autowired
    private SocketHandler socketHandler;

    @Async
    public void sayHello(String name) throws InterruptedException {
        Thread.sleep(6000);
        LoggerFactory.getLogger(HelloThread.class).info(name + ":Hello World!");
    }
}