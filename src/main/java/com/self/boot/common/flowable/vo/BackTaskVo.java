package com.self.boot.common.flowable.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 驳回实体vo
 *
 * @author jinyunliu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BackTaskVo extends BaseProcessVo {
    /**
     * 需要驳回的节点id 必填
     */
    private String distFlowElementId;
}
