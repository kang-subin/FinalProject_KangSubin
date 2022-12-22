package com.example.personalproject.controller;

import com.example.personalproject.domain.dto.Response;
import com.example.personalproject.domain.request.UserJoinRequest;
import com.example.personalproject.domain.response.UserJoinResponse;
import com.example.personalproject.domain.request.UserLoginRequest;
import com.example.personalproject.domain.response.UserLoginResponse;
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

    @PostMapping("/users/join")
    public Response join (@RequestBody UserJoinRequest userJoinRequest){
        UserJoinResponse userJoinResponse = userservice.join(userJoinRequest);
        return new Response().success(userJoinResponse);
    }

    @PostMapping("/users/login")
    public Response login(@RequestBody UserLoginRequest userLoginRequest){
        UserLoginResponse userLoginResponse = userservice.login(userLoginRequest);
        return new Response().success(userLoginResponse);
    }
}
