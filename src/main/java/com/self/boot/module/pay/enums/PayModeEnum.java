package com.self.boot.module.pay.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 支付方式的枚举
 */
@Getter
@RequiredArgsConstructor
public enum PayModeEnum {
    /**
     * 平台系统余额支付
     */
    BALANCE(0),

    /**
     * 微信支付
     */
    WECHAT(1),

    /**
     * 支付包支付
     */
    ALIPAY(2)

    ;

    private final int code;
}
