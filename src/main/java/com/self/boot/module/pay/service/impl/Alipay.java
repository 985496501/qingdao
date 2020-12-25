package com.self.boot.module.pay.service.impl;

import com.self.boot.module.pay.service.ModifiedPayService;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付宝支付实现
 */
@Slf4j
public class Alipay implements ModifiedPayService {
    @Override
    public Object pay(Object obj) {
        log.debug("我使用支付宝支付...............");
        return null;
    }

    @Override
    public void withdrawal() {

    }

    @Override
    public void refund() {

    }
}
