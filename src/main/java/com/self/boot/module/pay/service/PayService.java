package com.self.boot.module.pay.service;

import java.math.BigDecimal;

/**
 * 抽象支付功能
 */
public interface PayService {
    /**
     * 支付
     */
    Object pay(Object obj);

    /**
     * 查询余额
     *
     * @return money
     */
    BigDecimal queryBalance();

    /**
     * 提现
     */
    void withdrawal();

    /**
     * 退款
     */
    void refund();
}
