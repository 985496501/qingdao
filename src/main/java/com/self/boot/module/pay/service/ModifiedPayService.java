package com.self.boot.module.pay.service;

/**
 * 抽象支付功能
 * 调用第三方的支付不会被spring管理
 * 但是提供了一个支付支持类 统一提供支持
 */
public interface ModifiedPayService {
    /**
     * 支付
     */
    Object pay(Object obj);

    /**
     * 提现
     */
    void withdrawal();

    /**
     * 退款
     */
    void refund();
}
