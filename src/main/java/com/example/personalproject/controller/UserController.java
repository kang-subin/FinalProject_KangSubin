package com.example.personalproject.controller;

import com.example.personalproject.domain.dto.Response;
import com.example.personalproject.domain.dto.UserDto;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userservice;

    @PostMapping("/join")
    public Response<UserJoinResponse> join (@RequestBody UserJoinRequest userJoinRequest){
        UserDto userDto = userservice.join(userJoinRequest);
        return Response.success(new UserJoinResponse(userDto.getUserId(), userDto.getUserName()));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        String token = userservice.login(userLoginRequest);
        return Response.success(new UserLoginResponse(token));
    }
}
