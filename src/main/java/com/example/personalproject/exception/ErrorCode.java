package com.example.personalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATE_USER_NAME(HttpStatus.CONFLICT,"UserName이 중복됩니다.");
    private HttpStatus httpStatus;
    private String message;

}
