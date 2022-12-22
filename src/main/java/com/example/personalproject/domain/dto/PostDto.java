package com.example.personalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class PostDto {

    private Long id;
    private String title;
    private String body;

}
