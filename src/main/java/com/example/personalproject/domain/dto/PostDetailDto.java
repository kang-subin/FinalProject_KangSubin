package com.example.personalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PostDetailDto {
    private Long id;
    private String title;
    private String body;
    private String userName;
    private String createdAt;
    private String  lastModifiedAt;
}
