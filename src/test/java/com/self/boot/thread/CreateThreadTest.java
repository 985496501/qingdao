package com.self.boot.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class CreateThreadTest {

    @Test
    public void createThreadByRunnableTest() throws InterruptedException {
        Thread thread = new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        thread.join();

//        TimeUnit.SECONDS.sleep(5);
    }


    @Test
    public void createThreadByExtendTest() throws InterruptedException {
        new MyThread().start();

        TimeUnit.SECONDS.sleep(5);
    }


    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("hello thread");
        }
    }
}
