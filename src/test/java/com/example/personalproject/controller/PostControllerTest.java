package com.example.personalproject.controller;

import com.example.personalproject.domain.dto.PostDto;
import com.example.personalproject.domain.request.UserPostRequest;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @MockBean
    PostService postService;

    @Autowired
    MockMvc mockMvc;


    @Test
    @WithMockUser
    @DisplayName("포스트 작성 성공")
    public void write() throws Exception {

        UserPostRequest userPostRequest = new UserPostRequest("테스트", "안녕");
        PostDto postDto = new PostDto(1L, "테스트", "안녕");
        given(postService.write(any(),any())).willReturn(postDto);

        String url = "/api/v1/posts";
        String json = new ObjectMapper().writeValueAsString(userPostRequest);

        mockMvc.perform(post(url)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.message").value("포스트 등록 완료"))
                .andExpect(jsonPath("$.result.postId").value(1L))
                .andDo(print());
    }


    @Test
    @DisplayName("포스트 작성 실패(1) - 인증 실패 - JWT를 Bearer Token으로 보내지 않은 경우")
    public void writeFail() throws Exception {
        UserPostRequest userPostRequest = new UserPostRequest("테스트", "안녕");
        given(postService.write(any(),any())).willThrow(new UserException(ErrorCode.INVALID_PERMISSION,""));

        String url ="/api/v1/posts";
        String json = new ObjectMapper().writeValueAsString(userPostRequest);

        mockMvc.perform(post(url)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }






}


