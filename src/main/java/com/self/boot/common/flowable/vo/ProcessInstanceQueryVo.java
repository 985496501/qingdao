package com.self.boot.common.flowable.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 查询流程实例vo
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInstanceQueryVo implements Serializable {
    private String formName;
    private String userCode;
    private String userName;
}
