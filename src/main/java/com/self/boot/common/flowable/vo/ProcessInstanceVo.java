package com.self.boot.common.flowable.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程实例Vo
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
public class ProcessInstanceVo implements Serializable {

    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     * 流程定义id
     */
    private String processDefinitionId;
    /**
     * 激活状态 1激活 2挂起
     */
    private int suspensionState;
    /**
     * 表单名称
     */
    private String formName;
    /**
     * 表单主键
     */
    private String businessKey;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 审批人
     */
    private String approver;
    /**
     * 发起人
     */
    private String starter;
    /**
     * 发起人id
     */
    private String starterId;
    /**
     * 系统标识
     */
    private String systemSn;
}
