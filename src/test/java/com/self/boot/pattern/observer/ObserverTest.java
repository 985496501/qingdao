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
    /**
     * 　　　　    事件机制是基于观察者模式实现的。主要包括几下4个角色：
     * <p>
     * 　　　　　　事件源：触发事件的主体
     * <p>
     * 　　　　　　事件：事件本身，指的是EventObject中的source，具体可以是任何数据（包括事件源），用来传递数据
     * <p>
     * 　　　　　　事件监听器：当事件发生时，负责对事件的处理
     * <p>
     * 　　　　　　事件环境：整个事件所处的上下文，对整个事件提供支持
     */






















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

        public B() {
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
