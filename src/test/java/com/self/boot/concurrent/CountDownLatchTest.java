package com.self.boot.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

// versatile synchronization tool
@Slf4j
public class CountDownLatchTest {

    @Test
    public void onOffGateTest() {
        CountDownLatch l = new CountDownLatch(1);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                l.countDown();
                try {
                    l.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.debug("------- 执行任务 ----- ");
            }).start();
        }
    }


    @Test
    public void countDownTest() {
        CountDownLatch l = new CountDownLatch(1);
        l.countDown();
    }




}
