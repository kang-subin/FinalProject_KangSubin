package com.example.personalproject.domain;

import com.example.personalproject.domain.dto.UserJoinResponse;
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

    public Response success(UserJoinResponse userJoinResponse) {
        return Response.builder()
                .resultCode("SUCCESS")
                .result(userJoinResponse)
                .build();
    }
}