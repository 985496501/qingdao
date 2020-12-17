package com.self.boot.module.order.service;

import com.self.boot.dto.order.OrderDTO;

public interface OrderService {
    OrderDTO getOrderDTO(String orderNumber);
}
