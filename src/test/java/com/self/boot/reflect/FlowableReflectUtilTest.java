package com.self.boot.reflect;

import org.flowable.common.engine.impl.util.ReflectUtil;
import org.junit.jupiter.api.Test;

/**
 * @author: jinyun
 * @date: 2021/1/15
 */
public class FlowableReflectUtilTest {

    @Test
    public void loadClassTest() {
        Class<?> testExecutionListener = ReflectUtil.loadClass("com.self.boot.common.flowable.listener.TestExecutionListener");
        assert testExecutionListener != null;
    }
}
