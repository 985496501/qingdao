package com.self.boot.common.flowable.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询流程实例vo
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
public class ProcessInstanceQueryVo implements Serializable {
    private String formName;
    private String userCode;
    private String userName;
}
