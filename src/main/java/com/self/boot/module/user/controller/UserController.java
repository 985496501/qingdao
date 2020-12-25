package com.self.boot.module.user.controller;

import com.self.boot.module.user.entity.UserDO;
import com.self.boot.module.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDO> listUsers() {
        return userService.listUsers();
    }


    @PostMapping("/pwdLogin")
    public void login() {
        log.debug("=====================> login");
    }
}
