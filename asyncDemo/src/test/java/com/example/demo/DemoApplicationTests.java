package com.example.demo;

import com.example.demo.service.AsyncService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private AsyncService asyncService;

    @Test
    void contextLoads() throws InterruptedException {

    }

}
