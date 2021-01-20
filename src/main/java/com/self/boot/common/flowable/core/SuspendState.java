package com.self.boot.common.flowable.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 流程定义的激活或者挂起
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Getter
@RequiredArgsConstructor
public enum SuspendState {
    /**
     * 挂起
     */
    SUSPEND(0),
    /**
     * 激活
     */
    ACTIVE(1);

    private final int code;
}
