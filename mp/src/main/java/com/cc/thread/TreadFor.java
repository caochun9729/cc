package com.cc.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TreadFor {

    private static final int loopNum = 1*10;

    public static void main(String args[]) throws InterruptedException {
        TreadFor TestThreadPool = new TreadFor();
        long bt = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.addAll(list);
        list.addAll(list);
        //TestThreadPool.m1(list);
        long et2 = System.currentTimeMillis();
        System.out.println("[1]耗时:"+(et2 - bt)+ "ms");
        //TestThreadPool.m3(list);
        List<String> list1 = TestThreadPool.m3(list);
        System.out.println(list1.size());
    }

    public void m1( List<String> list) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        //模拟耗时操作
                        System.out.println("[1]" + Thread.currentThread().getName()+"----"+str);
                    } catch (Exception e) {
                    }
                }
            };
            pool.execute(run);
            //pool.submit(run);
        }

        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
        System.out.println("[1] done!");
    }

    public void m2() {
        AtomicInteger connectionIds = new AtomicInteger(0);
        for (int index = 0; index < loopNum; index++) {
            try {
                //模拟耗时操作
                Thread.sleep(1000);
                System.out.println("[2]" + Thread.currentThread().getName());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("[2] done!");
    }

    public List<String> m3( List<String> list) {
        List<String> a=new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(8);
        final CountDownLatch endGate = new CountDownLatch(list.size());
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);

                        //模拟耗时操作
                        System.out.println("[1]" + Thread.currentThread().getName()+"----"+str);
                        a.add(str);
                    } catch (Exception e) {
                    }finally {
                        endGate.countDown();
                    }
                }
            };
            pool.execute(run);
            //pool.submit(run);
        }
        pool.shutdown();
        try {
            endGate.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("[1] done!");
        return a;
    }

    public List<String> m4( List<String> list) {
        List<String> list1=new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        final CountDownLatch endGate = new CountDownLatch(list.size());
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        //模拟耗时操作
                        //System.out.println("[1]" + Thread.currentThread().getName()+"----"+str);
                        list1.add(str);
                    } catch (Exception e) {
                    }finally {
                        endGate.countDown();
                    }
                }
            };
            pool.execute(run);
            //pool.submit(run);
        }
        pool.shutdown();
        try {
            endGate.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list1;
    }


    public List<String> m5( List<String> list) {
        List<String> list1=new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        //模拟耗时操作
                        //System.out.println("[1]" + Thread.currentThread().getName()+"----"+str);
                        list1.add(str);
                    } catch (Exception e) {
                    }
                }
            };
            pool.execute(run);
            //pool.submit(run);
        }

        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
        return list1;
    }


}
