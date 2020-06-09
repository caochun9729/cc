package com.cc.thread1;

import java.util.concurrent.*;

/**
 * @Author cc
 * @Date 2020/6/8 18:00
 * @Version 1.0
 */
public class pooltest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        pooltest pooltest = new pooltest();
        pooltest.pooltest();
    }

    /**
     * 计算 400 数 和
     * 使用线程池
     */
    public void pooltest() throws ExecutionException, InterruptedException {
        //核心线程池大小
        int corePoolSize = 4;
        //最大线程池大小
        int maximumPoolSize = 4;
        //线程最大空闲时间
        long keepAliveTime = 1000;
        //时间单位
        TimeUnit unit = TimeUnit.MICROSECONDS;
        //线程等待队列
        BlockingQueue a = new SynchronousQueue();
        //线程创建工厂
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //拒绝策略
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        //创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, a, threadFactory, handler);

        // 创建 任务
        Task1 t1=new Task1(1,100);
        Task1 t2=new Task1(101,200);
        Task1 t3=new Task1(201,300);
        Task1 t4=new Task1(301,400);
//        让线程池自主选择一条线程执行线程任务
        Future<Integer>    f1=threadPoolExecutor.submit(t1);
        Future<Integer>    f2=threadPoolExecutor.submit(t2);
        Future<Integer>    f3=threadPoolExecutor.submit(t3);
        Future<Integer>    f4=threadPoolExecutor.submit(t4);

        threadPoolExecutor.shutdown();

        int sum1=f1.get();
        int sum2=f2.get();
        int sum3=f3.get();
        int sum4=f4.get();
        System.out.println(sum1+sum2+sum3+sum4);

    }

}
