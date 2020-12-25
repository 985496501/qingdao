package com.self.boot.app;

import com.self.boot.conf.TestConf;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 主要针对这个类测试 这个类完成了spring基于注解的ioc容器的初始化
 */
public class AnnotationConfigApplicationContextTest {

    /**
     * 查看了{@code Configuration}的javadoc
     *
     */
    @Test
    public void configurationTest() {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
        //
        acac.register(TestConf.class);
    }
}