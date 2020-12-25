package com.self.boot.pattern.singleton;

import org.junit.jupiter.api.Test;

/**
 * 这就是饿汉式的单例模式
 * one minute to finish hungry singleton pattern example.
 * typically defined as follows.
 * static public instance invokes instance-method
 */
public class HungrySingletonTest {
    public static final HungrySingletonTest INSTANCE = new HungrySingletonTest();

    @Override
    public String toString() {
        return "hello hungry singleton instance";
    }

    @Test
    public void innerTest() {
        System.out.println(HungrySingletonTest.INSTANCE.toString());
    }
}
