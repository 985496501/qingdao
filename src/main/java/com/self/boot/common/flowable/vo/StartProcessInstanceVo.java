package com.self.boot.common.flowable.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * 开启实例暴露的api的vo
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartProcessInstanceVo implements Serializable {
    /**
     * 流程定义key 必填
     */
    private String processDefinitionKey;
    /**
     * 业务系统id 必填
     */
    private String businessKey;
    /**
     * 启动流程变量 选填
     */
    private Map<String, Object> variables;
    /**
     * 申请人工号 必填
     */
    private String currentUserCode;
    /**
     * 系统标识 必填
     */
    private String systemSn;
    /**
     * 表单显示名称 必填
     */
    private String formName;
    /**
     * 流程提交人工号 必填
     */
    private String creator;
}
