package com.self.boot.module.pay.service.impl;

import com.self.boot.module.pay.service.ModifiedPayService;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付相关实现
 */
@Slf4j
public class WechatPay implements ModifiedPayService {

    @Override
    public Object pay(Object obj) {
        log.debug("我使用微信支付...............");
        return null;
    }

    @Override
    public void withdrawal() {

    }

    @Override
    public void refund() {

    }
}
