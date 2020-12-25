package com.self.boot.module.order.controller;

import com.self.boot.dto.order.OrderDTO;
import com.self.boot.common.exception.BaseException;
import com.self.boot.module.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderNumber}")
    public OrderDTO getOrderByNumber(@PathVariable("orderNumber") String orderNumber) {
        return orderService.getOrderDTO(orderNumber);
    }

    @GetMapping("/ex")
    public OrderDTO getEx() {
        throw new BaseException(500, "系统错误");
    }
}
