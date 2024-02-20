package com.heiyu.messaging.controller;

import java.util.Date;

import com.heiyu.messaging.request.ActivateUserRequest;
import com.heiyu.messaging.request.RegisterUserRequest;
import com.heiyu.messaging.request.UserLoginRequest;
import com.heiyu.messaging.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;
    // logout
    // forgetPassword
    // resetPassword
    // updateProfile
    // viewProfile GET


    @PostMapping("/register") // /users/register
    public String register(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {
        Date startTime = new Date();
        this.userService.register(registerUserRequest.getUsername(),
                registerUserRequest.getPassword(),
                registerUserRequest.getRepeatPassword(),
                registerUserRequest.getEmail(),
                registerUserRequest.getGender(),
                registerUserRequest.getAddress(),
                registerUserRequest.getNickname());
        Date endTime = new Date();
        log.info("Latency: {}, {}", endTime.getTime() - startTime.getTime(), 1);
        return registerUserRequest.toString();
    }

    @PostMapping("/activate")
    public String activate(@RequestBody ActivateUserRequest activateUserRequest) throws Exception {
            this.userService.activate(activateUserRequest.getUsername(), activateUserRequest.getValidationCode());
            return "";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest userLoginRequest) throws Exception {
            return this.userService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword());
    }
}

