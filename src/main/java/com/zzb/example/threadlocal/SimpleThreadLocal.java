package com.zzb.example.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadLocal的基本使用，每个线程执行前后，id是不变的，
 * 不受其他线程的影响
 *
 *
 * @author XBird
 * @date 2021/12/26
 **/
public class SimpleThreadLocal {
    private static boolean initData=false;
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            //线程在5s之后再次调用get方法获取ThreadLocal，是不变的，因为线程隔离
            Thread thread =new Thread(new ThreadId());
            thread.start();
        }
    }
}

class ThreadId implements Runnable{
    private static AtomicInteger id=new AtomicInteger(0);
    private static ThreadLocal<Integer> threadId=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return id.incrementAndGet();
        }
    };
    public static int get(){
        return threadId.get();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+";"+get());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+";"+get());
    }
}
