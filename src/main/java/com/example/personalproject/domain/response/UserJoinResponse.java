package com.example.personalproject.domain.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserJoinResponse {
    private Long userId;
    private String userName;

}
