package com.self.boot.app;

import org.junit.jupiter.api.Test;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.StartupStep;

public class StartupTest {
    @Test
    public void startupTest() {
        // 目前没有任何效果 说明数据没有收集到指定容器 没有打印出来
        // todo: 这个新提供的接口类 到底用于哪块的 metric ?
        StartupStep step = ApplicationStartup.DEFAULT.start("StartupTest.startupTest()");
        step.tag("step1", () -> "hello world");
        step.end();
    }
}
