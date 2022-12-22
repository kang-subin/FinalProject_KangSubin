package com.example.personalproject.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class UserPostResponse {
    private Long postId;
    private String message;

}
