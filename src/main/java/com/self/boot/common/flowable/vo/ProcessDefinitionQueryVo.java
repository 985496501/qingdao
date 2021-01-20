package com.self.boot.common.flowable.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 查询流程定义vo
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessDefinitionQueryVo implements Serializable {
    private String name;
    private String modelKey;
}
