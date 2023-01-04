package com.example.personalproject.controller;

import com.example.personalproject.domain.dto.CommentDto;
import com.example.personalproject.domain.dto.PostDetailDto;
import com.example.personalproject.domain.dto.PostDto;
import com.example.personalproject.domain.dto.Response;
import com.example.personalproject.domain.request.UserCommentRequest;
import com.example.personalproject.domain.request.UserPostEditRequest;
import com.example.personalproject.domain.request.UserPostRequest;
import com.example.personalproject.domain.response.*;
import com.example.personalproject.service.PostService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


import java.util.List;


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

    @GetMapping("/{postId}")
    public Response<UserPostDetailResponse>detail(@PathVariable Long postId){
        PostDetailDto postDetailDto = postService.detail(postId);
        return Response.success(new UserPostDetailResponse(postDetailDto.getId(), postDetailDto.getTitle(),
                                    postDetailDto.getBody(), postDetailDto.getUserName(),
                                    postDetailDto.getCreatedAt(),postDetailDto.getLastModifiedAt()));

    }

    @GetMapping("")
    public Response<PageImpl<UserPostDetailResponse>> list(){
        PageRequest pageRequest = PageRequest.of(0,20,Sort.by("id").descending());
        List<UserPostDetailResponse> list = postService.list(pageRequest);

        return Response.success(new PageImpl<>(list));
    }


    @DeleteMapping( "/{id}")
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

    @PostMapping("/{postsId}/comments")
    public Response<UserCommentResponse> comment_write(@RequestBody UserCommentRequest userCommentRequest, @PathVariable Long postsId, @ApiIgnore Authentication authentication){
        CommentDto commentDto = postService.comment_write(userCommentRequest, postsId, authentication);
        return Response.success(new UserCommentResponse(commentDto.getId(),commentDto.getComment(),commentDto.getUserName(),commentDto.getPostId(),commentDto.getCreatedAt()));
    }

    @PutMapping("/{postId}/comments/{id}")
    public Response<UserCommentEditResponse> comment_edit(@RequestBody UserCommentRequest userCommentRequest ,@PathVariable Long postId, @PathVariable Long id, @ApiIgnore Authentication authentication){
        String name = authentication.getName();
        CommentDto commentDto = postService.comment_edit(userCommentRequest,postId,id,name);
        return Response.success(new UserCommentEditResponse(commentDto.getId(),commentDto.getComment(),commentDto.getUserName(),commentDto.getPostId(),commentDto.getCreatedAt(),commentDto.getLastModifiedAt()));
    }

    @DeleteMapping("/{postId}/comments/{id}")
    public Response<UserCommentDeleteResponse> comment_delete(@PathVariable Long postId, @PathVariable Long id, @ApiIgnore Authentication authentication){
        String name = authentication.getName();
        CommentDto commentDto = postService.comment_delete(postId, id, name);
        return Response.success(new UserCommentDeleteResponse("댓글 삭제 완료",commentDto.getId()));
    }

    @GetMapping("/{postId}/comments")
    public Response<PageImpl<UserCommentResponse>> comment_list(@PathVariable Long postId, @RequestParam(name = "page") Integer page){
        PageRequest pageRequest = PageRequest.of(page,20,Sort.by("createdAt").descending());
        List<UserCommentResponse> list = postService.comment_list(postId,pageRequest);
        return Response.success(new PageImpl<>(list));
    }

    @GetMapping("/my")
    public Response<PageImpl<UserPostMyResponse>> post_my(@ApiIgnore Authentication authentication){
        String name = authentication.getName();
        PageRequest pageRequest = PageRequest.of(0,20,Sort.by("createdAt").descending());
        List<UserPostMyResponse> list = postService.post_my(name, pageRequest);
        return Response.success(new PageImpl<>(list));
    }


    @PostMapping("/{postId}/likes")
    public Response like(@PathVariable Long postId, @ApiIgnore Authentication authentication){
        String name = authentication.getName();
        Response response = postService.like(postId,name);
        return response;
    }

    @GetMapping("/{postId}/likes")
    public Response like_count(@PathVariable Long postId){
        Response response = postService.like_count(postId);
        return response;
    }

}
