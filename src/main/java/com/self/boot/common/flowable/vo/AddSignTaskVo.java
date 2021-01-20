package com.self.boot.common.flowable.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 加签vo
 *
 * @author jinyun
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddSignTaskVo extends BaseProcessVo {
    /**
     * 被加签人
     */
    private List<String> signPersoneds;
}
