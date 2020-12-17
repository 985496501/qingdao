package com.self.boot.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class OrderOutputTest {
    private final Object MONITOR = new Object();

    // 多种同步机制的运用

    /**
     * 多种同步机制  这个还是要学习 consist on learning.
     *
     *
     *
     * <p>
     * java写中间件
     * 线程模型 netty
     * <p>
     * 使用object的wait和notify方法实现
     * <p>
     * 必须让字符线程先获取任务执行权：
     * 如果digit线程先获取了锁 就一定先让它等待 释放掉锁。
     * <p>
     * <p>
     * <p>
     * char线程先执行  digit线程还没启动  当char线程唤醒是没有作用的，如果它等待了 digit线程启动 睡眠就死锁了
     *
     *
     * 使用wait和notify来解决这个事情是解决不了的, 这俩必须成对出现 而且必须是先notify然后wait
     * pack unpack 可以完成。
     *
     * @throws InterruptedException
     */
    @Test
    public void outputTest() throws InterruptedException {
        // a1b2c3d4e5f6 两个线程 一个线程输出顺序输出数字 一个线程输出字符
        char[] chars = new char[]{'a', 'b', 'c', 'd'};
        char[] digits = new char[]{'1', '2', '3', '4'};

        // 输出字符
        Thread aChar = new Thread(() -> {
            for (char c : chars) {
                synchronized (MONITOR) {
                    System.out.println(c);
                    try {
                        MONITOR.notify();
                        MONITOR.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    MONITOR.notify();
                }
            }

        }, "char");


        // 输出数字
        Thread digit = new Thread(() -> {
            for (char c : digits) {
                synchronized (MONITOR) {
                    try {
                        MONITOR.notify();
                        MONITOR.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(c);
                    MONITOR.notify();
                }
            }

        }, "digit");

        // c先执行 输出 唤醒 睡眠
        // d执行 唤醒c 睡眠 c唤醒d 执行完代码块放弃锁 d获取锁继续执行输出
        // 然后自己释放锁 给队列其他线程a执行 一次交替就完成了都完成了。
        aChar.start();
        //  TimeUnit.SECONDS.sleep(1);
        digit.start();

        TimeUnit.SECONDS.sleep(5);
    }
}
