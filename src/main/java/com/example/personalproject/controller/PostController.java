package com.example.personalproject.controller;

import com.example.personalproject.domain.dto.PostDto;
import com.example.personalproject.domain.dto.Response;
import com.example.personalproject.domain.request.UserPostRequest;
import com.example.personalproject.domain.response.UserPostResponse;
import com.example.personalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public Response<UserPostResponse> write (@RequestBody UserPostRequest userPostRequest){
        PostDto postDto = postService.write(userPostRequest);
        return Response.success(new UserPostResponse("포스트 등록 완료", postDto.getId()));
    }

}
