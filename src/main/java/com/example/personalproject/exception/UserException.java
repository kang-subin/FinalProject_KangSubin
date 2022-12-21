package com.example.personalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

}
