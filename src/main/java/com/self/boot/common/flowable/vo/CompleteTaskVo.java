package com.self.boot.common.flowable.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 执行任务vo
 *
 * @author jinyun
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CompleteTaskVo extends BaseProcessVo {
    /**
     * 任务参数 选填
     */
    private Map<String, Object> variables;
}
