package com.self.boot.common.conf;

import com.self.boot.dto.order.OrderDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConf {
    @Bean
    public OrderDTO orderDTO() {
        return new OrderDTO();
    }
}
