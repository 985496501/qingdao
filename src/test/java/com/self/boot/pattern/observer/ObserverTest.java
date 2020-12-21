package com.self.boot.pattern.observer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 * A -- B C
 * A的状态发生变化 要通知bc
 */
public class ObserverTest {
    //

    @Test
    public void obTest() {
        A a = new A();
        a.borrow(new B());
        a.borrow(new C());

        a.notifyList();
    }



    static class A {
        private final List<Subscriber> list = new ArrayList<>();

        public void borrow(Subscriber o) {
            list.add(o);
        }

        public void notifyList() {
            if (list.size() > 0) {
                // functionalInterface
                list.forEach(Subscriber::lend);
            }
        }
    }

    // 订阅者接口 抽象出一个接口
    // 不然无法调用不同对象的同一方法
    // 应该是订阅的者的回调方法
    // 接口抽象的能力 就不一样了
    @FunctionalInterface
    interface Subscriber {
        void lend();
    }

    static class B implements Subscriber {

        public  B () {
            System.out.println("我是B我要借钱了");
        }


        @Override
        public void lend() {
            System.out.println("别人通知我要还我钱了B");
        }
    }

    static class C implements Subscriber {

        public C() {
            System.out.println("我是C我要借钱了");
        }

        @Override
        public void lend() {
            System.out.println("别人通知我要还我钱了C");
        }
    }



}
