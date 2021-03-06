package com.self.boot.common.flowable.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程视图的基类方法
 * 针对于流程实例
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
public abstract class BaseProcessVo implements Serializable {
    /**********************任务相关的参数**********************/
    /**
     * 任务id 必填
     */
    private String taskId;

    /**********************审批意见的参数**********************/

    /**
     * 操作人code 必填
     */
    private String userCode;
    /**
     * 审批意见 必填
     */
    private String message;
    /**
     * 流程实例的id 必填
     */
    private String processInstanceId;
    /**
     * 审批类型 必填
     */
    private String type;
}
