package com.example.personalproject.controller;

import com.example.personalproject.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@WebMvcTest(AlarmController.class)
class AlarmControllerTest {

    @MockBean
    PostService postService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("알람 목록 조회 성공")
    @WithMockUser
    public void alarm(){

        String url = "/api/v1/alarms";

    }

}