package com.example.personalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;


@AllArgsConstructor
@Getter
public class ListResponse <T> {

    private String resultCode;
    private T result;
    private Pageable pageable;


    public static <T> ListResponse<T> success(T result, Pageable pageable) {

        return new ListResponse<>("SUCCESS",result,pageable);

    }
    }

