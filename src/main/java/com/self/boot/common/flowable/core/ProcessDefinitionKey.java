package com.self.boot.common.flowable.core;

/**
 * 业务系统中所有的流程创建--发布--然后需要硬编码
 * 在此写入 流程定义 key
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
public interface ProcessDefinitionKey {
    /**
     * 测试流程
     */
    String TEST = "test";

    String HOLIDAY = "holiday";
}
