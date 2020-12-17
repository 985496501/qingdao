package com.self.boot.module.order.service.impl;

import com.self.boot.dto.order.OrderDTO;
import com.self.boot.module.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private static final AtomicInteger LEFT = new AtomicInteger(50);

    @Override
    @Cacheable(cacheNames = "order", key = "#orderNumber", cacheManager = "redisCacheManager")
    public OrderDTO getOrderDTO(String orderNumber) {
        log.info("===============================>>>>> \n select from DB, orderNumber: {}", orderNumber);
        return new OrderDTO(orderNumber, "adidas", "shoes", LEFT.decrementAndGet());
    }
}
