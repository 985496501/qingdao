package com.self.boot.pattern.factory;

import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

/**
 * 工厂模式 常常用于生产对象
 */
public class SimpleFactoryTest {

    @Test
    public void Test() {
        DefaultFactory defaultFactory = DefaultFactory.getInstance();
        A bean = defaultFactory.createBean(A.class);
        System.out.println(bean);
    }

    // 先创建要给泛型类 并抽象接口
    // 泛型就是java编译器把.java 编译成.class的时候类型擦除
    // 会把泛型抹掉 直接把真实的类型填充进去 就是下面
    // 泛型也是抽象 约束抽象方法 同一泛型 这个泛型往往是一个接口规范
    // 真正实现的时候 是不允许使用泛型类的

    // interface SimpleFactory {
    //    User createBean(Class<User> clazz);
    // }
    interface SimpleFactory<T> {
        T createBean(Class<T> clazz);
    }

    // 工程只能生产特定的一个对象 削弱抽象
    // 有些源码经常出现 uncheck 就是没有检查泛型的类型
    // 待定 这个可以看 spring security 的源码 看看它的泛型 或者说接口设计
    static abstract class AbstractDefaultFactory implements SimpleFactory<A> {
        // no wildcard expected
    }

    // ???
    // todo: 使用工厂模式 目的是 什么 呢 究竟有什么好处呢？？？ 探索
    // 以及其他 创建对象的方式 比如 构建者模式  通过内置静态对象的引用  attr_populate



    // 看别的开源中是怎么使用工厂模式的 spring是如何使用的
    static class DefaultFactory implements SimpleFactory<A> {
        private static final DefaultFactory DEFAULT_FACTORY = new DefaultFactory();

        public static DefaultFactory getInstance() {
            return DEFAULT_FACTORY;
        }

        @Override
        public A createBean(Class<A> clazz) {
            try {
//                if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                Constructor<A> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
//                }
            } catch (Exception e) {
                throw new RuntimeException("no args constructor is necessary");
            }
        }
    }

    // 我们最好也是生产出特定功能的对象来这样可以用接口进行抽象
    // 好处不用强转 直接可以调用 接口的实现子类
    @FunctionalInterface
    interface English {
        void listen();
    }

    @ToString
    @Getter
    static class A implements English {
        private int i;
        @Override
        public void listen() {
            System.out.println("english must be listen more often");
        }
    }
}
