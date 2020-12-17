package com.self.boot.constant;

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
}
