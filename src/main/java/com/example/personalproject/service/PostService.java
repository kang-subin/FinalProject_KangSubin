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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public PostDto write(UserPostRequest userPostRequest, String name) {

        User user = userRepository.findByUserName(name).orElseThrow(()-> new UserException(ErrorCode.INVALID_TOKEN,"잘못된 token 입니다."));

        Post post = Post.builder()
                .userName(name)
                .title(userPostRequest.getTitle())
                .body(userPostRequest.getBody())
                .build();

        Post saved = postRepository.save(post);

        return new PostDto(saved.getId(),saved.getTitle(),saved.getBody());

    }

    public PostDetailDto detail(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new UserException(ErrorCode.POST_NOT_FOUND, "해당 포스트가 없습니다."));

        PostDetailDto postDetailDto = PostDetailDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .userName(post.getUserName())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();

                 return postDetailDto;

    }

    public PostDto delete(Long id){

     if(postRepository.findById(id).isEmpty()) throw new UserException(ErrorCode.POST_NOT_FOUND,"해당 포스트가 없습니다.");

         postRepository.deleteById(id);

         return PostDto.builder()
             .id(id)
             .build();
    }

}








