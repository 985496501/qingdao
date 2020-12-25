package com.self.boot.common.constant;

/**
 * 有关缓存相关的常量统一定义
 */
public interface CacheKeyConst {
    /**
     * key的分隔符
     */
    String DELIMITER = ":";

    /**
     * 优惠券的有效期 缓存优惠券有效期
     */
    String COUPON_EXPIRED_KEY = "coupon:id:";

    /**
     * 用户token
     * user:token:`token`= {'保存所有的用户信息'} 保存当前线程变量里面
     */
    String USER_TOKEN = "user:token:";
}
