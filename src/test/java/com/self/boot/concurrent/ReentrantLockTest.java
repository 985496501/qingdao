package com.self.boot.concurrent;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockTest {
    private static final Map<String, String> map = new ConcurrentHashMap<>(8 << 1);

    private static final ReentrantLock lock = new ReentrantLock();

    private static int count = 10000000;

    @Test
    public void lockTest() {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();
        try {
            writeLock.lock();
            map.put("name", "liujinyun");
        } finally {
            writeLock.unlock();
        }

        // otherwise

        System.out.println(map);
    }

    // reentrantLock的经典用法 AQS CAS的实现了锁 用了状态量
    // 可重入锁： 可以多次获取锁 必须手动释放锁就是一定要在finally{ lock.unlock() }
    @Test
    public void reentrantLockTest() throws InterruptedException {
        // classic usage
        for (int j = 0; j <= 4; j++) {
            new Thread(() -> {
                try {
                    lock.lock();
                    while (count > 0) {
                        System.out.println(--count);
                    }
                } finally {
                    lock.unlock();
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(5);
    }

    // 相比sync condition就是一大增强
    @Test
    public void conditionTest() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        // like monitor's wait notify 方法
        Condition condition = lock.newCondition();

        // CAS锁  比较交换锁 配合 volatile 使用
        lock.lock();

//        new Thread(() -> {
//            lock.lock();
//        }).start();
//
//        new Thread(() -> {
//
//        }).start();


        TimeUnit.SECONDS.sleep(5);

    }

    // 如果一个数组满了就等待进入 如果一个数组为空就等待获取
    // 使用两个condition
    @Test
    public void conditionExampleTest() {

    }


    static class BoundedBuffer {
        // 所有都是非静态的因为我们使用容器都是new 公用一个实例 多个线程同时操作这个容器
        final int len = 1024;
        final Object[] buffer = new Object[len];
        final Lock lock = new ReentrantLock();
        final Condition notFull;
        final Condition notEmpty;
        int count, putPtr, takePtr = 0;

        public BoundedBuffer() {
            notEmpty = lock.newCondition();
            notFull = lock.newCondition();
        }

      //`  ArrayBlockingQueue
        public void put(Object o) {
            lock.lock();
            try {
                // while
                if (putPtr == len) {

                }
            } finally {
                lock.unlock();
            }
        }
    }
}
