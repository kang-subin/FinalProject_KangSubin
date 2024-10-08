package com.example.personalproject.controller;

import com.example.personalproject.domain.dto.Response;
import com.example.personalproject.domain.dto.UserDto;
import com.example.personalproject.domain.request.UserJoinRequest;
import com.example.personalproject.domain.request.UserLoginRequest;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.service.UserService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    Response response;

    @Test
    @WithMockUser
    @DisplayName("회원가입 성공")
    public void join() throws Exception {

        UserJoinRequest userJoinRequest = new UserJoinRequest("강제리", "1234");
        UserDto userDto = new UserDto(1L,userJoinRequest.getUserName(),userJoinRequest.getPassword());
        given(userService.join(any())).willReturn(userDto);


        String url = "/api/v1/users/join";
        String json = new ObjectMapper().writeValueAsString(userJoinRequest);

        mockMvc.perform(post(url)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.userId").exists())
                .andExpect(jsonPath("$.result.userName").value("강제리"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입 실패 - userName 중복인 경우")
    public void joinFail () throws Exception {
        UserJoinRequest userJoinRequest = new UserJoinRequest("강수빈", "1234");
        given(userService.join(any())).willThrow(new UserException(ErrorCode.DUPLICATE_USER_NAME,userJoinRequest.getUserName()+"은 이미 있습니다."));

        String url = "/api/v1/users/join";
        String json = new ObjectMapper().writeValueAsString(userJoinRequest);

        mockMvc.perform(post(url)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.resultCode").value("ERROR"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.errorCode").value("DUPLICATE_USER_NAME"))
                .andExpect(jsonPath("$.result.message").value(userJoinRequest.getUserName() + "은 이미 있습니다."))
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 성공")
    public void login() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest("강수빈","1234");
        String token = "token";
        given(userService.login(any())).willReturn(token);

        String url ="/api/v1/users/login";
        String json = new ObjectMapper().writeValueAsString(userLoginRequest);

        mockMvc.perform(post(url)
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.jwt").exists())
                .andDo(print());
        }


    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 회원 가입 된 userName 없는 경우")
    public void loginUserNameFail() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest("테스트","1234");
        given(userService.login(any())).willThrow(new UserException(ErrorCode.USERNAME_NOT_FOUND,"Not founded"));

        String url ="/api/v1/users/login";
        String json = new ObjectMapper().writeValueAsString(userLoginRequest);

        mockMvc.perform(post(url)
                        .with(csrf())
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
    @WithMockUser
    @DisplayName("로그인 실패 - password틀림")
    public void loginPasswordFail() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest("강수빈","1111");
        given(userService.login(any())).willThrow(new UserException(ErrorCode.INVALID_PASSWORD,"패스워드가 잘못되었습니다."));

        String url ="/api/v1/users/login";
        String json = new ObjectMapper().writeValueAsString(userLoginRequest);
        mockMvc.perform(post(url)
                        .with(csrf())
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










