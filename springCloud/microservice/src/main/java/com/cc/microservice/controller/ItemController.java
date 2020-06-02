package com.cc.microservice.controller;

import com.cc.microservice.entity.Item;
import com.cc.microservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 对外提供接口服务，查询商品信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "item/{id}")
    public Item queryItemById(@PathVariable("id") Long id) {
        return itemService.queryItemById(id);
    }

    @GetMapping(value = "/getItem")
    public Item getItem(@RequestParam("id") Long id,@RequestParam("name") String code) {
        return itemService.queryItemById(id);
    }

    @PostMapping(value = "/postItem")
    public Item postItem(@RequestBody Item item) {
        return item;
    }
}
