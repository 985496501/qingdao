package com.self.boot.common.flowable.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务转办vo
 *
 * @author jinyun
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TurnTaskVo extends BaseProcessVo {

    /**
     * 被转办人工号 必填
     */
    private String turnToUserId;
}
