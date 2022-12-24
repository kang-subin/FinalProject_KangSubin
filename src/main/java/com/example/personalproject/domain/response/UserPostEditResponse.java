package com.example.personalproject.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class UserPostEditResponse {
    private String message;
    private Long postId;
}
