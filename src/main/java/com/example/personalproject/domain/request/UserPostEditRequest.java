package com.example.personalproject.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPostEditRequest {
    private String title;
    private String body;
}
