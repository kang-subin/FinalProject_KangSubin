package com.example.personalproject.controller;


import com.example.personalproject.domain.dto.PostDetailDto;
import com.example.personalproject.domain.dto.PostDto;
import com.example.personalproject.domain.request.UserPostEditRequest;
import com.example.personalproject.domain.request.UserPostRequest;
import com.example.personalproject.domain.response.UserPostDetailResponse;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        given(postService.write(any(), any())).willReturn(postDto);

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
    @WithMockUser
    @DisplayName("포스트 작성 실패(1) - 인증 실패 - JWT를 Bearer Token으로 보내지 않은 경우")
    public void writeFail() throws Exception {
        UserPostRequest userPostRequest = new UserPostRequest("테스트", "안녕");
        given(postService.write(any(), any())).willThrow(new UserException(ErrorCode.INVALID_PERMISSION, ""));

        String url = "/api/v1/posts";
        String json = new ObjectMapper().writeValueAsString(userPostRequest);

        mockMvc.perform(post(url)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @WithMockUser
    @DisplayName("포스트 작성 실패(2) - 인증 실패 - JWT가 유효하지 않은 경우")
    public void writeFail2() throws Exception {
        UserPostRequest userPostRequest = new UserPostRequest("테스트", "안녕");
        given(postService.write(any(), any())).willThrow(new UserException(ErrorCode.INVALID_PERMISSION, ""));

        String url = "/api/v1/posts";
        String json = new ObjectMapper().writeValueAsString(userPostRequest);

        mockMvc.perform(post(url)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @WithMockUser
    @DisplayName("포스트 상세")
    public void detail() throws Exception {

        PostDetailDto postDetailDto = new PostDetailDto(1L, "테스트", "안녕하세요", "강수빈",LocalDateTime.now(),LocalDateTime.now());
        Long id = 1L;
        given(postService.detail(any())).willReturn(postDetailDto);

        String url ="/api/v1/posts/1";

        mockMvc.perform(get(url)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.id").value(1L))
                .andExpect(jsonPath("$.result.title").value("테스트"))
                .andExpect(jsonPath("$.result.userName").value("강수빈"))
                .andExpect(jsonPath("$.result.createdAt").exists())
                .andDo(print());

    }

    @Test
    @WithMockUser
    @DisplayName("포스트 삭제 성공")
    public void deleteSuccess() throws Exception {

        Long id = 1L;
        String name = "강수빈";
        String url ="/api/v1/posts/{id}";
        PostDto postDto = PostDto.builder()
                .id(id)
                .build();

        given(postService.delete(any(),any())).willReturn(postDto);

        mockMvc.perform(delete(url,id)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.message").value("포스트 삭제 완료"))
                .andExpect(jsonPath("$.result.postId").value(1L))
                .andDo(print());

    }

    @Test
    @WithMockUser
    @DisplayName("포스트 삭제 실패(1) - 인증 실패")
    public void deleteFail() throws Exception {

        Long id = 1L;
        String name = "강수빈";
        String url ="/api/v1/posts/{id}";

        given(postService.delete(any(), any())).willThrow(new UserException(ErrorCode.INVALID_PERMISSION, ""));
        mockMvc.perform(delete(url,id)
                        .with(csrf()))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @WithMockUser
    @DisplayName("포스트 삭제 실패(2) - 작성자 불일치")
    public void deleteFail2 () throws Exception {

        Long id = 1L;
        String name = "강수빈";
        String url ="/api/v1/posts/{id}";

        given(postService.delete(any(), any())).willThrow(new UserException(ErrorCode.INVALID_PERMISSION,"사용자가 권한이 없습니다."));
        mockMvc.perform(delete(url,id)
                .with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.resultCode").value("ERROR"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.errorCode").value("INVALID_PERMISSION"))
                .andExpect(jsonPath("$.result.message").value("사용자가 권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("포스트 삭제 실패(3)- 데이터베이스 에러")

    public void deleteFail3 () throws Exception {
        Long id = 1L;
        String name = "강수빈";
        String url ="/api/v1/posts/{id}";

        given(postService.delete(any(), any())).willThrow(new UserException(ErrorCode.DATABASE_ERROR,"DB에러"));
        mockMvc.perform(delete(url,id)
                        .with(csrf()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.resultCode").value("ERROR"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.errorCode").value("DATABASE_ERROR"))
                .andExpect(jsonPath("$.result.message").value("DB에러"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("포스트 수정 성공")
    public void edit() throws Exception {
        Long id = 1L;
        String name = "강수빈";
        String url ="/api/v1/posts/{id}";

        UserPostEditRequest userPostEditRequest = new UserPostEditRequest("수정 성공", "안녕");
        String json = new ObjectMapper().writeValueAsString(userPostEditRequest);

        PostDto postDto = new PostDto(1L, "수정 성공", "안녕");


        given(postService.edit(any(),any(),any())).willReturn(postDto);
        mockMvc.perform(put(url,id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.message").value("포스트 수정 완료"))
                .andExpect(jsonPath("$.result.postId").value(1L))
                .andDo(print());

    }

    @Test
    @WithMockUser
    @DisplayName("포스트 수정 실패(1)- 인증 실패")
            public void editFail() throws Exception {

        Long id = 1L;
        String name = "강수빈";
        String url ="/api/v1/posts/{id}";


        UserPostEditRequest userPostEditRequest = new UserPostEditRequest("수정 성공", "안녕");
        String json = new ObjectMapper().writeValueAsString(userPostEditRequest);

    given(postService.edit(any(), any(),any())).willThrow(new UserException(ErrorCode.INVALID_PERMISSION, ""));
        mockMvc.perform(put(url,id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
            .andExpect(status().isUnauthorized())
            .andDo(print());

}


    @Test
    @WithMockUser
    @DisplayName("포스트 수정 실패(2)- 작성자 불일치")
    public void editFail2() throws Exception {

        Long id = 1L;
        String name = "강수빈";
        String url ="/api/v1/posts/{id}";


        UserPostEditRequest userPostEditRequest = new UserPostEditRequest("수정 성공", "안녕");
        String json = new ObjectMapper().writeValueAsString(userPostEditRequest);

        given(postService.edit(any(), any(),any())).willThrow(new UserException(ErrorCode.INVALID_PERMISSION, ""));
        mockMvc.perform(put(url,id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @WithMockUser
    @DisplayName("포스트 수정 실패(3)- 데이터베이스 에러")
    public void editFail3() throws Exception {

        Long id = 1L;
        String name = "강수빈";
        String url ="/api/v1/posts/{id}";

        UserPostEditRequest userPostEditRequest = new UserPostEditRequest("수정 성공", "안녕");
        String json = new ObjectMapper().writeValueAsString(userPostEditRequest);

        given(postService.edit(any(), any(),any())).willThrow(new UserException(ErrorCode.DATABASE_ERROR, "DB에러"));

        mockMvc.perform(put(url,id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError())
                .andDo(print());

    }

    @Test
    @WithMockUser
    @DisplayName("포스트 리스트 - 조회성공")
    public void list() throws Exception {

        String url ="/api/v1/posts";
        PageRequest pageRequest = PageRequest.of(0,20, Sort.by("id").descending());
        ArgumentCaptor<UserPostDetailResponse> responseList = ArgumentCaptor.forClass(UserPostDetailResponse.class);

        given(postService.list(pageRequest)).willReturn(responseList.getAllValues());
        mockMvc.perform(get(url)
                        .with(csrf()))
                .andExpect(status().isOk());


    }


    }










