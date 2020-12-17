package com.self.boot.common.listener;

import com.self.boot.constant.CacheKeyConst;
import com.self.boot.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
//@Component
public class RedisMessageListener extends KeyExpirationEventMessageListener {
    private static final AtomicInteger AUTO_INCREMENT = new AtomicInteger();
    private static final Topic KEY_EVENT_EXPIRED_TOPIC = new PatternTopic("__keyevent@15__:expired");

    public RedisMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void doRegister(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, KEY_EVENT_EXPIRED_TOPIC);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String cacheEntry = new String(message.getBody());
        System.out.println(cacheEntry + "=============>>>>>" + AUTO_INCREMENT.getAndIncrement());

        if (cacheEntry.startsWith(CacheKeyConst.COUPON_EXPIRED_KEY)) {
            log.info("优惠券id: {}", KeyUtil.getCacheId(cacheEntry));
            log.info("根据优惠券id修改数据库该优惠券的使用状态等");
        }
    }
}
