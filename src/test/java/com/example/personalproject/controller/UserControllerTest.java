package com.example.personalproject.controller;

import com.example.personalproject.domain.dto.Response;
import com.example.personalproject.domain.request.UserJoinRequest;
import com.example.personalproject.domain.request.UserLoginRequest;
import com.example.personalproject.domain.response.UserJoinResponse;
import com.example.personalproject.domain.response.UserLoginResponse;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    Response response;

    @Test
    @DisplayName("회원가입 성공")
    public void join() throws Exception {

        UserJoinRequest userJoinRequest = new UserJoinRequest("강수빈", "1111");
        UserJoinResponse userJoinResponse = new UserJoinResponse(1L,userJoinRequest.getUserName());
        given(userService.join(any())).willReturn(userJoinResponse);


        String url = "/api/v1/join";
        String json = new ObjectMapper().writeValueAsString(userJoinRequest);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.userId").exists())
                .andExpect(jsonPath("$.result.userName").value("강수빈"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - userName 중복인 경우")
    public void joinFail () throws Exception {
        UserJoinRequest userJoinRequest = new UserJoinRequest("강수빈", "1111");
        given(userService.join(any())).willThrow(new UserException(ErrorCode.DUPLICATE_USER_NAME,userJoinRequest.getUserName()+"은 이미 있습니다."));

        String url = "/api/v1/join";
        String json = new ObjectMapper().writeValueAsString(userJoinRequest);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.resultCode").value("ERROR"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.errorCode").value("DUPLICATE_USER_NAME"))
                .andExpect(jsonPath("$.result.message").value(userJoinRequest.getUserName() + "은 이미 있습니다."))
                .andDo(print());
    }

    @Test // 실패함 ..
    @DisplayName("로그인 성공")
    public void login() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest("강수빈","1111");
        String token = "token";
        given(userService.login(any())).willReturn(new UserLoginResponse(token));

        String url ="/api/v1/login";
        String json = new ObjectMapper().writeValueAsString(userLoginRequest);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.jwt").exists())
                .andDo(print());
        }


    @Test
    @DisplayName("로그인 실패 - 회원 가입 된 userName 없는 경우")
    public void loginUserNameFail() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest("테스트용","1111");
        given(userService.login(any())).willThrow(new UserException(ErrorCode.USERNAME_NOT_FOUND,"Not founded"));

        String url ="/api/v1/login";
        String json = new ObjectMapper().writeValueAsString(userLoginRequest);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultCode").value("ERROR"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.errorCode").value("USERNAME_NOT_FOUND"))
                .andExpect(jsonPath("$.result.message").value("Not founded"))
                .andDo(print());
    }


    @Test
    @DisplayName("로그인 실패 - password틀림")
    public void loginPasswordFail() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest("테스트용","1111");
        given(userService.login(any())).willThrow(new UserException(ErrorCode.INVALID_PASSWORD,"패스워드가 잘못되었습니다."));

        String url ="/api/v1/login";
        String json = new ObjectMapper().writeValueAsString(userLoginRequest);
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.resultCode").value("ERROR"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.errorCode").value("INVALID_PASSWORD"))
                .andExpect(jsonPath("$.result.message").value("패스워드가 잘못되었습니다."))
                .andDo(print());
    }


    }










