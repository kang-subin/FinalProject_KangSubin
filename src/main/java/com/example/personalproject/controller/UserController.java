package com.example.personalproject.controller;

import com.example.personalproject.domain.Response;
import com.example.personalproject.domain.UserRequest;
import com.example.personalproject.domain.UserResponse;
import com.example.personalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public Response<UserResponse> join (Response response, @RequestBody UserRequest userRequest){
        UserResponse userResponse = userservice.join(userRequest);
        return response.success(userResponse);
    }

}
