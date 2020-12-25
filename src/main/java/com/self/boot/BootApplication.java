package com.self.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
//@Import(MyCustomFunc.class) 直接ioc注入这个类的实例对象
@SpringBootApplication //(exclude = RedisAutoConfiguration.class)
public class BootApplication implements CommandLineRunner {

    // todo： 源码阅读到事件发布的异步操作中出现了瓶颈 2020/12/22 跳过
    // 查看ioc初始化过程
    public static void main(String[] args) {
        try {
            SpringApplication.run(BootApplication.class, args);
        } catch (Exception e) {
          //  e.printStackTrace();
        }
    }

    // 1. 注意springboot的启动流程 这个是整个应用的启动 包括ioc的初始化 tomcat启动 spring的整个发布订阅模式
    //    redis连接过程 数据库的连接 各种初始化过程 各种 extensions 这个是极其重要的 spring的巨大优势就在于高可扩展 和 定制
    // 2. ioc的初始化流程 也叫 spring bean的生命周期 包括bean的生命周期和一个事件 和 哪些扩展点
    // 两者的区别

    /**
     * 整个应用启动完毕 会回调这个方法 还有一个类 ApplicationRunner
     */
    @Override
    public void run(String... args) {
        // 可以开启 netty
        // 可以预先加载 缓存文件
        log.debug("==========================>>>>  应用加载完毕, 回调方法 执行逻辑, 比如同时开启socket服务器, 加载缓存文件");
    }
}
