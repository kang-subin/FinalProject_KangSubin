package com.example.personalproject.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class UserLoginRequest {

    private String userName;
    private String password;

}
