package com.self.boot.util;

import com.self.boot.constant.CacheKeyConst;
import org.springframework.util.StringUtils;

public abstract class KeyUtil {

    public static String combineCacheEntry(String cacheKeyConst, Long id) {
        return cacheKeyConst + id;
    }

    public static Long getCacheId(String cacheEntry) {
        if (StringUtils.isEmpty(cacheEntry)) {
            return null;
        }

        return Long.parseLong(cacheEntry.substring(cacheEntry.lastIndexOf(CacheKeyConst.DELIMITER) + 1));
    }

    public static void main(String[] args) {
        Long cacheId = getCacheId(combineCacheEntry(CacheKeyConst.COUPON_EXPIRED_KEY, 1L));
        System.out.println(cacheId);
    }
}
