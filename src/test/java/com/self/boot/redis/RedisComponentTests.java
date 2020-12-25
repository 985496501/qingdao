package com.self.boot.redis;

import com.self.boot.common.redis.RedisService;
import com.self.boot.common.constant.CacheKeyConst;
import com.self.boot.dto.coupon.CouponDTO;
import com.self.boot.dto.order.OrderDTO;
import com.self.boot.module.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.CacheKeyPrefix;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
@SpringBootTest
class RedisComponentTests {
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderService orderService;

    @Test
    public void setTest() {
        redisService.set("insurance:pacific:generate", 123456789L, 60);
        redisService.del("insurance:pacific:generate");
        boolean b = redisService.setIfAbsent("insurance:pacific:generate", 123456789L, 60);
        System.out.println(b);
    }

    @Test
    public void objectValueTest() {
        CouponDTO couponDTO = new CouponDTO(1L, BigDecimal.TEN, new Date());
        redisService.set(CacheKeyConst.COUPON_EXPIRED_KEY + couponDTO.getId(), 7);
//        SimpleUser o = (SimpleUser)redisService.get("user:info:" + simpleUser.getUserName());
//        log.info("{}", o.toString());
    }

    @Test
    public void cacheKeyPrefixTest() {
        CacheKeyPrefix simple = CacheKeyPrefix.simple();
        String id = simple.compute("id");
        System.out.println(id);
        System.out.println(simple);
    }

    @Test
    public void ttlTest() {
        OrderDTO orderDTO = orderService.getOrderDTO("123456789");
        System.out.println(orderDTO);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String userName;
        private String password;
        private String phoneNumber;
        private String email;
        private String realName;
        private LocalDate birthday;
        private Date createdAt;
        private SimpleUser simpleUser;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class SimpleUser {
        private String userName;
        private String password;
    }

}
