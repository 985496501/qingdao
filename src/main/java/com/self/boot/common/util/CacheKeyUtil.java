package com.self.boot.common.util;

import com.self.boot.common.constant.CacheKeyConst;
import org.springframework.util.StringUtils;

/**
 * redis key 操作的工具类
 */
public abstract class CacheKeyUtil {

    public static String combineCacheEntry(String cacheKeyConst, Long id) {
        return cacheKeyConst + id;
    }

    public static Long getCacheId(String cacheEntry) {
        if (StringUtils.hasLength(cacheEntry)) {
            return null;
        }

        return Long.parseLong(cacheEntry.substring(cacheEntry.lastIndexOf(CacheKeyConst.DELIMITER) + 1));
    }

    public static void main(String[] args) {
        Long cacheId = getCacheId(combineCacheEntry(CacheKeyConst.COUPON_EXPIRED_KEY, 1L));
        System.out.println(cacheId);
    }
}
