package com.example.personalproject.domain;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class UserRequest {

    private String userName;
    private String password;
}
