package com.example.springbootdemoentity.entity;

import lombok.Data;

/**
 * @Author cc
 * @Date 2020/10/30 11:16
 * @Version 1.0
 */
@Data
public class Product {
    private String name;
    private int age;
    private String add;
    private String email;

    public Product() {
        this.name = "name";
        this.age = 12;
        this.add = "北京市历史互通";
        this.email = "6666.qq.com";
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", add='" + add + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
