package com.example.personalproject.controller;

import com.example.personalproject.domain.dto.ListResponse;
import com.example.personalproject.domain.dto.PostDetailDto;
import com.example.personalproject.domain.dto.PostDto;
import com.example.personalproject.domain.dto.Response;
import com.example.personalproject.domain.request.UserPostEditRequest;
import com.example.personalproject.domain.request.UserPostRequest;
import com.example.personalproject.domain.response.UserPostDetailResponse;
import com.example.personalproject.domain.response.UserPostDeleteResponse;
import com.example.personalproject.domain.response.UserPostEditResponse;
import com.example.personalproject.domain.response.UserPostResponse;
import com.example.personalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping("")
    public Response<UserPostResponse>write(@RequestBody UserPostRequest userPostRequest, @ApiIgnore Authentication authentication) {
        String name = authentication.getName();
        PostDto postDto = postService.write(userPostRequest, name);
        return Response.success(new UserPostResponse("포스트 등록 완료", postDto.getId()));
    }

    @GetMapping(value = "/{postId}")
    public Response<UserPostDetailResponse>detail(@PathVariable Long postId){
        PostDetailDto postDetailDto = postService.detail(postId);
        return Response.success(new UserPostDetailResponse(postDetailDto.getId(), postDetailDto.getTitle(),
                                    postDetailDto.getBody(), postDetailDto.getUserName(),
                                    postDetailDto.getCreatedAt(),postDetailDto.getLastModifiedAt()));

    }

    @GetMapping("")
    public ListResponse<List<UserPostDetailResponse>> list(Pageable pageable){
        List<UserPostDetailResponse> list = postService.list(pageable);
        return ListResponse.success(list, pageable);
    }


    @DeleteMapping(value = "/{id}")
    public Response<UserPostDeleteResponse>delete(@PathVariable Long id, @ApiIgnore Authentication authentication){

        String name =authentication.getName();
        PostDto postDto = postService.delete(id, name);
        return Response.success(new UserPostDeleteResponse("포스트 삭제 완료",id));

    }

    @PutMapping("/{id}")
    public Response<UserPostEditResponse>edit(@PathVariable Long id, @ApiIgnore Authentication authentication, @RequestBody UserPostEditRequest userPostEditRequest){
        String name = authentication.getName();
        PostDto postDto = postService.edit(id,name,userPostEditRequest);
        return Response.success(new UserPostEditResponse("포스트 수정 완료",postDto.getId()));

    }

}
