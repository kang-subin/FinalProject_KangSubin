package com.example.personalproject.service;

import com.example.personalproject.domain.dto.PostDetailDto;
import com.example.personalproject.domain.dto.PostDto;
import com.example.personalproject.domain.entity.Post;
import com.example.personalproject.domain.entity.User;
import com.example.personalproject.domain.request.UserPostEditRequest;
import com.example.personalproject.domain.request.UserPostRequest;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class PostServiceTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private PostRepository postRepository = Mockito.mock(PostRepository.class);

    private CommentRepository commentRepository = Mockito.mock(CommentRepository.class);

    private LikeRepository likeRepository = Mockito.mock(LikeRepository.class);

    private AlarmRepository alarmRepository = Mockito.mock(AlarmRepository.class);

    private PostService postService;

    @BeforeEach
    void setup() {
        postService = new PostService(postRepository, userRepository, commentRepository,likeRepository,alarmRepository);
    }

    @Test
    @DisplayName("포스트 등록 성공")
    public void write() {

        String name = "강수빈";
        UserPostRequest userPostRequest = new UserPostRequest("제목", "내용");

        User user = new User(1L, "강수빈", "1111");
        Post post = new Post(1L, "제목", "내용", user);

        when(userRepository.findByUserName(any())).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        PostDto postDto = postService.write(userPostRequest, name);

        assertEquals(1L, postDto.getId());
        assertEquals("제목", postDto.getTitle());
        assertEquals("내용", postDto.getBody());

    }


    @Test
    @DisplayName("포스트 등록 실패 - 유저가 존재하지 않을 때")
    public void writeFail() {

        String name = "강제리";
        UserPostRequest userPostRequest = new UserPostRequest("제목", "내용");

        User user = new User(1L, "강수빈", "1111");

        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());

        UserException exception = Assertions.assertThrows(UserException.class, () -> postService.write(userPostRequest, name));

        assertEquals(ErrorCode.INVALID_TOKEN, exception.getErrorCode());
        assertEquals("잘못된 토큰입니다.", exception.getErrorCode().getMessage());

    }

    @Test
    @DisplayName("포스트 상세 - 조회성공")
    public void detail() {

        Long postId = 1L;
        User user = new User(1L, "강수빈", "1111");
        Post post = new Post(1L, "제목", "내용", user);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        PostDetailDto postDetailDto = postService.detail(postId);

        assertEquals(1L, postDetailDto.getId());
        assertEquals("제목", postDetailDto.getTitle());
        assertEquals("내용", postDetailDto.getBody());
        assertEquals("강수빈", postDetailDto.getUserName());
        assertEquals(post.getCreatedAt(), postDetailDto.getCreatedAt());
        assertEquals(post.getLastModifiedAt(), postDetailDto.getLastModifiedAt());


    }

    @Test
    @DisplayName("포스트 삭제 실패 - 유저 존재하지 않음")
    public void deleteFail() {

        Long id = 1L;
        String name = "강수빈";
        User user = new User(1L, "강수빈", "1111");
        Post post = new Post(1L, "제목", "내용", user);

        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());

        UserException exception = Assertions.assertThrows(UserException.class, () -> postService.delete(id, name));

        assertEquals(ErrorCode.USERNAME_NOT_FOUND, exception.getErrorCode());
        assertEquals("Not founded", exception.getErrorCode().getMessage());

    }

    @Test
    @DisplayName("포스트 삭제 실패 - 포스트 존재하지 않음")
    public void deleteFail2() {

        Long id = 1L;
        String name = "강수빈";
        User user = new User(1L, "강수빈", "1111");
        Post post = new Post(1L, "제목", "내용", user);

        when(userRepository.findByUserName(any())).thenReturn(Optional.of(user));
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        UserException exception = Assertions.assertThrows(UserException.class, () -> postService.delete(id, name));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
        assertEquals("해당 포스트가 없습니다.", exception.getErrorCode().getMessage());

    }

    @Test
    @DisplayName("포스트 수정 실패 - 포스트 존재하지 않음")
    public void editFail(){
        Long id = 1L;
        String name = "강수빈";
        User user = new User(1L, "강수빈", "1111");
        Post post = new Post(1L, "제목", "내용", user);
        UserPostEditRequest userPostEditRequest = new UserPostEditRequest("수정","실패");

        when(userRepository.findByUserName(any())).thenReturn(Optional.of(user));
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        UserException exception = Assertions.assertThrows(UserException.class, () -> postService.edit(id, name,userPostEditRequest));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
        assertEquals("해당 포스트가 없습니다.", exception.getErrorCode().getMessage());

    }

    @Test
    @DisplayName("포스트 수정 실패 - 작성자!=유저")
    public void editFail2(){
        Long id = 1L;
        String name = "강제리";
        User user = new User(2L,"강제리","1234"); // findByUserName 한 user

        User user2 = new User(1L, "강수빈", "1111"); // findById 한 post 에 들어있는 user
        Post post = new Post(1L, "제목", "내용", user2);
        UserPostEditRequest userPostEditRequest = new UserPostEditRequest("수정","실패");

        when(userRepository.findByUserName(any())).thenReturn(Optional.of(user));
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        UserException exception = Assertions.assertThrows(UserException.class, () -> postService.edit(id, name,userPostEditRequest));

        assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
        assertEquals("사용자가 권한이 없습니다.", exception.getErrorCode().getMessage());

    }

    @Test
    @DisplayName("포스트 수정 실패 - 유저 존재하지 않음")
    public void editFail3(){
        Long id = 1L;
        String name = "강제리";

        User user = new User(1L, "강수빈", "1111");
        Post post = new Post(1L, "제목", "내용", user);
        UserPostEditRequest userPostEditRequest = new UserPostEditRequest("수정","실패");

        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        UserException exception = Assertions.assertThrows(UserException.class, () -> postService.edit(id, name,userPostEditRequest));

        assertEquals(ErrorCode.USERNAME_NOT_FOUND, exception.getErrorCode());
        assertEquals( "Not founded", exception.getErrorCode().getMessage());

    }

}
