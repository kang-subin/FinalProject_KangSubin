package com.example.personalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserJoinResponse {
    private Long userId;
    private String userName;

}
