package com.example.personalproject.service;

import com.example.personalproject.domain.dto.PostDetailDto;
import com.example.personalproject.domain.dto.PostDto;
import com.example.personalproject.domain.entity.Post;
import com.example.personalproject.domain.entity.User;
import com.example.personalproject.domain.request.UserPostRequest;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.repository.PostRepository;
import com.example.personalproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class PostServiceTest {

  private UserRepository userRepository = Mockito.mock(UserRepository.class);

  private PostRepository postRepository = Mockito.mock(PostRepository.class);

  private PostService postService;

  @BeforeEach
  void setup(){

      postService = new PostService(postRepository, userRepository);
  }

@Test
@DisplayName("포스트 등록 - 작성 성공")
public void write(){

      String name = "강수빈";
      UserPostRequest userPostRequest = new UserPostRequest("제목","내용");

      User user = new User(1L,"강수빈","1111");
      Post post = new Post(1L,"제목","내용",user);

      when(userRepository.findByUserName(any())).thenReturn(Optional.of(user));
      when(postRepository.save(any())).thenReturn(post);

      PostDto postDto = postService.write(userPostRequest,name);

      assertEquals(1L,postDto.getId());
      assertEquals("제목",postDto.getTitle());
      assertEquals("내용",postDto.getBody());

}


@Test
@DisplayName("포스트 실패 - 유저가 존재하지 않을 때")
public void writeFail(){

    String name = "강제리";
    UserPostRequest userPostRequest = new UserPostRequest("제목","내용");

    User user = new User(1L,"강수빈","1111");

    when(userRepository.findByUserName(any())).thenReturn(Optional.empty());

    UserException exception = Assertions.assertThrows(UserException.class, ()-> postService.write(userPostRequest,name));

    assertEquals(ErrorCode.INVALID_TOKEN, exception.getErrorCode());
    assertEquals("잘못된 토큰입니다.", exception.getErrorCode().getMessage());

    }

    @Test
    @DisplayName("포스트 상세 - 조회성공")
    public void detail(){

      Long postId = 1L;
      User user = new User(1L,"강수빈","1111");
      Post post = new Post(1L,"제목","내용",user);

      when(postRepository.findById(postId)).thenReturn(Optional.of(post));

      PostDetailDto postDetailDto =  postService.detail(postId);

      assertEquals(1L,postDetailDto.getId());
      assertEquals("제목",postDetailDto.getTitle());
      assertEquals("내용",postDetailDto.getBody());
      assertEquals("강수빈",postDetailDto.getUserName());
      assertEquals(post.getCreatedAt(),postDetailDto.getCreatedAt());
      assertEquals(post.getLastModifiedAt(),postDetailDto.getLastModifiedAt());


    }




    }
