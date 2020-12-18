package com.self.boot.os;

import org.junit.jupiter.api.Test;

public class ProcessorTest {

    @Test
    public void getOsProcessorTest() {
        // 获取运行的可获取的处理器核心数  6
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
    }

}
