package com.self.boot.module.pay.service.impl;

import com.self.boot.module.pay.service.ModifiedPayService;
import lombok.extern.slf4j.Slf4j;

/**
 * 平台余额支付方式具体实现
 */
@Slf4j
public class PlatformBal implements ModifiedPayService {

    @Override
    public Object pay(Object obj) {
        log.debug("我使用余额支付...............");
        return null;
    }

    @Override
    public void withdrawal() {

    }

    @Override
    public void refund() {

    }
}
