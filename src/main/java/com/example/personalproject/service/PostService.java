package com.example.personalproject.service;

import com.example.personalproject.domain.dto.PostDto;
import com.example.personalproject.domain.entity.Post;
import com.example.personalproject.domain.request.UserPostRequest;
import com.example.personalproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostDto write(UserPostRequest userPostRequest) {

        Post post = Post.builder()
                .title(userPostRequest.getTitle())
                .body(userPostRequest.getBody())
                .build();

        Post saved = postRepository.save(post);

        return new PostDto(saved.getId(), saved.getTitle(), saved.getBody());

    }
}

