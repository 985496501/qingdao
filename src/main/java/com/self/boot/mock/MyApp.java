package com.self.boot.mock;

public class MyApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        Object o = appConfig.getBean("mock");
        System.out.println(o);
    }
}
