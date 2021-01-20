package com.self.boot.common.flowable.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 委派任务vo
 *
 * @author jinyun
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DelegateTaskVo extends BaseProcessVo {
    /**
     * 委派人
     */
    private String delegateUserCode;
}
