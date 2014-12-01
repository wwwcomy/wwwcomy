package com.iteye.wwwcomy.thread;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 测试读写锁
 * 
 * @author xingnan.liu
 */
public class TestReadWriteLock {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // 10个读线程
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "读取到的数据为：" + new Data().get());
                }
            }).start();
        }
        // 10个写线程
        for (int i = 0; i < 10; i++) {
            final int num = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Data().set("world" + num);
                }
            }).start();
        }
    }

}

// 数据源
class Data {
    private String name = "hello";
    ReadWriteLock rwl = new ReentrantReadWriteLock();

    public String get() {
        rwl.readLock().lock();
        System.out.println(Thread.currentThread().getName() + "--读取数据前：");

        try {
            try {
                Thread.sleep(new Random().nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name;
        } finally {
            System.out.println(Thread.currentThread().getName() + "--已经读取完");
            rwl.readLock().unlock();
        }

    }

    public void set(String name) {
        rwl.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + ">写入数据前");
            try {
                Thread.sleep(new Random().nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.name = name;
            System.out.println(Thread.currentThread().getName() + ">写完数据");
        } finally {
            rwl.writeLock().unlock();
        }

    }
}
