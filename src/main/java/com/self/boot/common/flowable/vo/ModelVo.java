package com.self.boot.common.flowable.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程定义模型
 *
 * @author jinyun
 */
@Data
public class ModelVo implements Serializable {
    /**
     * 流程定义id
     */
    private String processId;

    /**
     * 流程名称name
     */
    private String processName;

    /**
     * 分类Id
     */
    private String categoryId;

    /**
     * 流程的xml
     */
    private String xml;
}
