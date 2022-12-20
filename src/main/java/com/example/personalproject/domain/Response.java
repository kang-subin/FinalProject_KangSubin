package com.example.personalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Response<T> {

    private String resultCode;
    private T result;

    public Response success(UserResponse userResponse) {
        return Response.builder()
                .resultCode("SUCCESS")
                .result(userResponse)
                .build();
    }
}