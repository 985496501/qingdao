package com.self.boot.common.flowable.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务记录
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
public class TaskVo implements Serializable {
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 审批人
     */
    private String approver;
    /**
     * 审批人id
     */
    private String approverId;
    /**
     * 表单名称
     */
    private String formName;
    /**
     * 业务主键
     */
    private String businessKey;
    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 开始时间
     */
    private Date startTime ;

    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 系统标识
     */
    private String systemSn;
}
