package com.pst.login.controller;

import com.pst.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/otp/{aadhaarNumber}")
    public String generateAndSendOtp(@PathVariable long aadhaarNumber) {
        return loginService.generateAndSendOtp(aadhaarNumber);
    }

}
