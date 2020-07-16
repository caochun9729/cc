package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author cc
 * @Date 2020/7/16 13:56
 * @Version 1.0
 * 订单信息类
 */
@Data
public class OrderInfo {
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 商品sku
     */
    private Long skuId;

    /**
     * 商品价格
     */
    private BigDecimal price;

    public OrderInfo(String userId, Long orderId, Long skuId, BigDecimal price) {
        this.userId = userId;
        this.orderId = orderId;
        this.skuId = skuId;
        this.price = price;
    }

}
