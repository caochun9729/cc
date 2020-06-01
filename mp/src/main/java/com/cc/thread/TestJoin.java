package com.cc.thread;

public class TestJoin {
    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        ThreadTest t1=new ThreadTest("A");
        ThreadTest t2=new ThreadTest("B");
        System.out.println("t1start");
        t1.start();
        t1.join();
        System.out.println("t1end");
        System.out.println("t2start");
        t2.start();
        t2.join();
        System.out.println("t2end");

    }


}
class ThreadTest extends Thread {
    private String name;
    public ThreadTest(String name){
        this.name=name;
    }
    public void run(){
        for(int i=1;i<=5;i++){
            System.out.println(name+"-"+i);
        }
    }

}
