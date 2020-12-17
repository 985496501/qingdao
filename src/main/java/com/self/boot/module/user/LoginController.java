package com.self.boot.module.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    @PostMapping("/pwdLogin")
    public void login() {
        log.debug("=====================> login");
    }
}
