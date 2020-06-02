package com.cc.microserviceorder.controller;

import com.cc.microserviceorder.entity.Order;
import com.cc.microserviceorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "order/{orderId}")
    public Order queryOrderById( @PathVariable("orderId") String orderId) {
        return this.orderService.queryOrderById(orderId);
    }

    @PostMapping("/test1")
    public Order test1(@RequestBody Order order){
        return order;
    }
}
