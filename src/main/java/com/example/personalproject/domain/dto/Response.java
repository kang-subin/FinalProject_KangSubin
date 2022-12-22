package com.example.personalproject.domain.dto;

import com.example.personalproject.domain.response.UserJoinResponse;
import com.example.personalproject.domain.response.UserLoginResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class Response <T> {

    private String resultCode;
    private T result;


    public static <T> Response <T> success(T result) {
        return new Response<>("SUCCESS", result);
    }


    public static <T> Response <T> error(String resultCode, T result) {

        return new Response(resultCode,result);
    }
}