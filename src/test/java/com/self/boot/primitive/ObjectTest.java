package com.self.boot.primitive;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ObjectTest {

    public static boolean objEqual(Object o1, Object o2) {
        if (o1 == null)
            return o2 == null;
        else
            return o1.equals(o2);
    }


    public static void main(String[] args) {
        Object o = new Object();
        boolean b = ObjectTest.objEqual(o, o);
        System.out.println(b);
    }



    @Test
    public void objectClassTest() {
        A a = new A();
        Class<? extends A> aClass = a.getClass();
        A b = new A();
        Class<? extends A> aClass1 = b.getClass();
        System.out.println(aClass == aClass1);

        System.out.println(aClass.equals(aClass1));
    }

    @Test
    public void cloneTest() {
        A a = new A();
    }

    @Test
    public void notifyTest() throws InterruptedException {
        new Thread(()->{
            // current thread acquires monitor.
            synchronized (this)  {
                System.out.println("我是A线程 我先获取 monitor");
                try {
                    System.out.println("我暂时释放 monitor");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("我是A线程 我已经执行完我的任务了");
            }
        }, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            synchronized (this)  {
                System.out.println("我是B线程 我获取了 monitor");

                System.out.println("我要执行B任务了");
                // 我先叫醒人家再睡
                this.notify();
                System.out.println("我唤醒睡眠的A线程继续执行");
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        TimeUnit.SECONDS.sleep(5);
    }



    private static class A { }
}
