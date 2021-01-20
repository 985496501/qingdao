package com.self.boot.common.flowable.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程定义
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
public class ProcessDefinitionVo implements Serializable {
    protected String id;
    protected String modelKey;
    protected String name;
    protected int version;
    protected String category;
    protected String deploymentId;
    protected String resourceName;
    protected String dgrmResourceName;
    protected int suspensionState;
    protected String tenantId;
}
