package com.example.personalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATE_USER_NAME(HttpStatus.CONFLICT,"UserName이 중복됩니다."),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND,"Not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"패스워드가 잘못되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"잘못된 토큰입니다."),

    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED,"사용자가 권한이 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 포스트가 없습니다."),

    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"데이터베이스 에러"),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 댓글이 없습니다."),

    DUPLICATE_LIKE(HttpStatus.CONFLICT,"이미 좋아요 한 포스트입니다."),

    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 포스트에 좋아요가 없습니다.");

    private HttpStatus httpStatus;
    private String message;

}
