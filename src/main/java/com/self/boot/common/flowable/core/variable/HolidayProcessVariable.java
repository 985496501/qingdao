package com.self.boot.common.flowable.core.variable;

/**
 * test 流程定义的 key 的直接变量
 * 用户发布的时候直接动态嵌入
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
public interface HolidayProcessVariable {
    String MANAGER = "manager";

    String DIRECTOR = "director";

    String DAYS = "days";

    String APPROVAL = "approval";
}
