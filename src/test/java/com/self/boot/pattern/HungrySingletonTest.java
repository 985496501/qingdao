package com.self.boot.pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * 这就是饿汉式的单例模式
 * one minute to finish hungry singleton pattern example.
 */
public class HungrySingletonTest {
    @Getter
    @Setter
    private String name;

    private HungrySingletonTest (String name) {
        this.name = name;
    }

    @Getter
    private static final HungrySingletonTest INSTANCE = new HungrySingletonTest("superman");
}
