package com.self.boot.flowable;

/**
 * @author: jinyun
 * @date: 2021/1/15
 */
public interface FlowableTestConst {
    String TENANT_ID = "00000";

    // 流程定义的key  格式： PROC_DEF_KEY_*  按照需求自定义工作流 名称就是 key.bpmn20.xml
    String PROC_DEF_KEY_TEST = "test";

    // 业务id 例如订单id or  orderNumber 关联业务的
    String BUSINESS_KEY = "123465";
}
