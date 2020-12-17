package com.self.boot.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class PoolTest {



    @Test
    public void poolExecutorTest() throws ExecutionException, InterruptedException {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(2);
        ScheduledFuture<String> schedule = threadPool.schedule(() -> {
            TimeUnit.SECONDS.sleep(10);
            return "hello world";
        }, 0, TimeUnit.SECONDS);

        String s = schedule.get();
        System.out.println(s);
    }

    @Test
    public void customPoolTest() {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1024);
        for (int i = 0; i < 1024; i++) {
            queue.add(() -> System.out.println("hello world"));
        }

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 10,
                0, TimeUnit.MILLISECONDS, queue);
        if (queue.size() == 0) {
            return;
        }
    }

    public static void main(String[] args) {
//        BigDecimal reduce = list.stream().map(WithdrawalItemData::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//        System.out.println(reduce);
    }
}
