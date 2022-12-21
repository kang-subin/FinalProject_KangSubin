package com.example.personalproject.domain.dto;

import com.example.personalproject.domain.response.UserJoinResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Response <T> {

    private String resultCode;
    private T result;



    public static Response success(UserJoinResponse userJoinResponse) {
        return Response.builder()
                .resultCode("SUCCESS")
                .result(userJoinResponse)
                .build();
    }

    public static <T> Response <T> error(String resultCode, T result) {

        return new Response(resultCode,result);
    }
}