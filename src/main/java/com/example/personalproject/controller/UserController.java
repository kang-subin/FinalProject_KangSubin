package com.example.personalproject.controller;

import com.example.personalproject.domain.Response;
import com.example.personalproject.domain.dto.UserJoinRequest;
import com.example.personalproject.domain.dto.UserJoinResponse;
import com.example.personalproject.domain.dto.UserLoginRequest;
import com.example.personalproject.domain.dto.UserLoginResponse;
import com.example.personalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userservice;

    @PostMapping("/join")
    public Response join (@RequestBody UserJoinRequest userJoinRequest){
        UserJoinResponse userJoinResponse = userservice.join(userJoinRequest);
        return new Response().success(userJoinResponse);
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest userLoginRequest){
        UserLoginResponse userLoginResponse = userservice.login(userLoginRequest);
        return userLoginResponse;
    }
}
