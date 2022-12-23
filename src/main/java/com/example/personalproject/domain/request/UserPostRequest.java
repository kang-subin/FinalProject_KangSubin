package com.example.personalproject.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class UserPostRequest {
    private String title;
    private String body;
}
