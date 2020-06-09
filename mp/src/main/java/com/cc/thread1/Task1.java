package com.cc.thread1;

import java.util.concurrent.Callable;

/**
 * @Author cc
 * @Date 2020/6/8 17:59
 * @Version 1.0
 */
public class Task1 implements Callable {
    private int num1;
    private int num2;

    public Task1(){}
    public Task1(int num1,int num2){
        this.num1=num1;
        this.num2=num2;

    }


    @Override
    public Integer call() throws Exception {
        int sum=0;
        for(int i=num1;i<=num2;i++){
            sum+=i;
        }
        return sum;
    }

}
