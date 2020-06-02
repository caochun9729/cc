package com.cc.microservice.service;

import com.cc.microservice.entity.Item;

public interface ItemService {
    /**
     * 模拟实现商品查询
     *
     * @param id
     * @return
     */
    public Item queryItemById(Long id);
}
