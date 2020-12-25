package com.self.boot.module.pay.controller;

import com.self.boot.module.pay.enums.PayModeEnum;
import com.self.boot.module.pay.service.PaySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pay")
public class PayController {
    @Autowired
    private PaySupport paySupport;

    @PostMapping
    public Object pay(HttpServletRequest request) {
        return paySupport.pay(PayModeEnum.ALIPAY, new Object());
    }
}
