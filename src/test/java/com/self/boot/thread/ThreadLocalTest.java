package com.self.boot.thread;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void threadLocalTest() {
        Thread thread = Thread.currentThread();



        // 让一个线程死循环就get一个公共map的值
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        //
        threadLocal.set("0");
        System.out.println(threadLocal.get());
        threadLocal.remove();
    }
}
