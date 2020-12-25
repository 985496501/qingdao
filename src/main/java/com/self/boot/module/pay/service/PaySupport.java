package com.self.boot.module.pay.service;

import com.self.boot.module.pay.enums.PayModeEnum;
import com.self.boot.module.pay.service.impl.Alipay;
import com.self.boot.module.pay.service.impl.PlatformBal;
import com.self.boot.module.pay.service.impl.WechatPay;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用这个支付组件即可
 * 其实这样使用不符合面向接口编程的思想
 *
 * todo: 看看 开源框架 是怎么维护 远程调用交互 和  本系统内部的业务的？
 */
@Component
public class PaySupport {
    private static final Map<PayModeEnum, ModifiedPayService> SERVICE_MAP = new HashMap<>(4);

    static {
        // 仅仅创建这一次 实现对象
        SERVICE_MAP.put(PayModeEnum.BALANCE, new PlatformBal());
        SERVICE_MAP.put(PayModeEnum.WECHAT, new WechatPay());
        SERVICE_MAP.put(PayModeEnum.ALIPAY, new Alipay());
    }

    public Object pay(PayModeEnum payModeEnum, Object o) {
        return SERVICE_MAP.get(payModeEnum).pay(o);
    }

    public void refund(PayModeEnum payModeEnum, Object o) {
        SERVICE_MAP.get(payModeEnum).refund();
    }

    public void withdrawal(PayModeEnum payModeEnum, Object o) {
        SERVICE_MAP.get(payModeEnum).withdrawal();
    }
}
