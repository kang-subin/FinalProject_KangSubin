package com.example.personalproject.exception;

import com.example.personalproject.domain.dto.ErrorResponse;
import com.example.personalproject.domain.dto.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(UserException.class)
        public ResponseEntity<?> UserExceptionHandler(UserException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(),e.getMessage());
            return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                    .body(Response.error("ERROR",errorResponse));
        }

}
