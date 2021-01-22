package com.self.boot.common.flowable.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 执行任务 传入当事人code
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskQueryVo implements Serializable {

    /**
     * 用户工号
     */
    private String userCode;
}
