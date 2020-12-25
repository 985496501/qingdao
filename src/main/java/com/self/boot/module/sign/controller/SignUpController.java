package com.self.boot.module.sign.controller;

import com.self.boot.module.sign.dto.SignUpDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign")
public class SignUpController {

    @PostMapping("/up")
    public void signUp(@RequestBody SignUpDTO sign) {

    }
}
