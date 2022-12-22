package com.example.personalproject.service;

import com.example.personalproject.JwtTokenUtil.JwtTokenUtil;
import com.example.personalproject.domain.dto.PostDto;
import com.example.personalproject.domain.entity.Post;
import com.example.personalproject.domain.request.UserPostRequest;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.repository.PostRepository;
import com.example.personalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto write(UserPostRequest userPostRequest, String name) {

        userRepository.findByUserName(name).orElseThrow(()-> new UserException(ErrorCode.INVALID_TOKEN,"잘못된 token 입니다."));

        Post post = Post.builder()
                .title(userPostRequest.getTitle())
                .body(userPostRequest.getBody())
                .build();

        Post saved = postRepository.save(post);

        return new PostDto(saved.getId(), saved.getTitle(), saved.getBody());

    }
}

