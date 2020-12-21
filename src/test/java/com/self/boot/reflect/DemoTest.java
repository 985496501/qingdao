package com.self.boot.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

public class DemoTest {

    // 判断一个字节码文件是不是接口是不是抽象方法是不是子类
    @Test
    public void demoTest() {
        Class<A> clazz = A.class;
        System.out.println(clazz.isInterface());
        Class<B> bClass = B.class;
        System.out.println(Modifier.isAbstract(bClass.getModifiers()));
        System.out.println(canBeInitialized(C.class));
    }

    private boolean canBeInitialized(Class<?> clazz) {
        // modifier 是限定性 修饰符
        return !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
    }

    interface A {

    }

    abstract class B implements A {

    }

    class C extends B {

    }
}
