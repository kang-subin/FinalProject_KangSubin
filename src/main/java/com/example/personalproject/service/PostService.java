package com.example.personalproject.service;

import com.example.personalproject.domain.dto.CommentDto;
import com.example.personalproject.domain.dto.PostDetailDto;
import com.example.personalproject.domain.dto.PostDto;
import com.example.personalproject.domain.dto.Response;
import com.example.personalproject.domain.entity.Comment;
import com.example.personalproject.domain.entity.Like;
import com.example.personalproject.domain.entity.Post;
import com.example.personalproject.domain.entity.User;
import com.example.personalproject.domain.request.UserCommentRequest;
import com.example.personalproject.domain.request.UserPostEditRequest;
import com.example.personalproject.domain.request.UserPostRequest;
import com.example.personalproject.domain.response.UserCommentResponse;
import com.example.personalproject.domain.response.UserPostDetailResponse;
import com.example.personalproject.domain.response.UserPostMyResponse;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.repository.CommentRepository;
import com.example.personalproject.repository.LikeRepository;
import com.example.personalproject.repository.PostRepository;
import com.example.personalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final LikeRepository likeRepository;

    public PostDto write(UserPostRequest userPostRequest, String name) {

        User user = userRepository.findByUserName(name).orElseThrow(() -> new UserException(ErrorCode.INVALID_TOKEN, "잘못된 token 입니다."));

        Post post = Post.builder()
                .user(user)
                .title(userPostRequest.getTitle())
                .body(userPostRequest.getBody())
                .build();

        Post saved = postRepository.save(post);

        return new PostDto(saved.getId(), saved.getTitle(), saved.getBody());

    }

    public PostDetailDto detail(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException(ErrorCode.POST_NOT_FOUND, "해당 포스트가 없습니다."));

        PostDetailDto postDetailDto = PostDetailDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .userName(post.getUser().getUserName())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();

        return postDetailDto;

    }

    public PostDto delete(Long id, String name) {

        Optional<Post> post = postRepository.findById(id);

        User user = userRepository.findByUserName(name)
                .orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, "Not founded"));


        if (post.isEmpty()) throw new UserException(ErrorCode.POST_NOT_FOUND, "해당 포스트가 없습니다.");
        if (!(user.getUserName().equals(post.get().getUser().getUserName())))
            throw new UserException(ErrorCode.INVALID_PERMISSION, "사용자가 권한이 없습니다.");

        postRepository.deleteById(id);

        return PostDto.builder()
                .id(id)
                .build();
    }

    public List<UserPostDetailResponse> list(Pageable pageable) {

        Page<Post> list = postRepository.findAll(pageable);

        List<UserPostDetailResponse> responseList = list.stream()
                .map(lists -> UserPostDetailResponse.builder()
                        .id(lists.getId())
                        .title(lists.getTitle())
                        .body(lists.getBody())
                        .userName(lists.getUser().getUserName())
                        .createdAt(lists.getCreatedAt())
                        .lastModifiedAt(lists.getLastModifiedAt())
                        .build())
                .collect(Collectors.toList());

        return responseList;
    }

    public PostDto edit(Long id, String name, UserPostEditRequest userPostEditRequest) {

        Optional<Post> post = postRepository.findById(id);

        User user = userRepository.findByUserName(name)
                .orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, "Not founded"));

        if (post.isEmpty()) throw new UserException(ErrorCode.POST_NOT_FOUND, "해당 포스트가 없습니다.");
        if (!(user.getUserName().equals(post.get().getUser().getUserName())))
            throw new UserException(ErrorCode.INVALID_PERMISSION, "사용자가 권한이 없습니다.");


        Post postEdit = new Post();

        postEdit.setId(id);
        postEdit.setTitle(userPostEditRequest.getTitle());
        postEdit.setBody(userPostEditRequest.getBody());
        postEdit.setUser(user);
        postEdit.setCreatedAt(LocalDateTime.now());


        Post saved = postRepository.saveAndFlush(postEdit);

        return PostDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .body(saved.getBody())
                .build();
    }

    public CommentDto comment_write(UserCommentRequest userCommentRequest, Long postId, Authentication authentication) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException(ErrorCode.POST_NOT_FOUND, "해당 포스트가 없습니다."));
        User commentUser = userRepository.findByUserName(authentication.getName()).orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, "Not founded"));

        Comment comment = Comment.builder()
                .comment(userCommentRequest.getComment())
                .post(post)
                .user(commentUser)
                .build();

        Comment saved = commentRepository.save(comment);

        CommentDto commentDto = CommentDto.builder()
                .id(saved.getId())
                .comment(saved.getComment())
                .userName(saved.getUser().getUserName())
                .postId(saved.getPost().getId())
                .createdAt(saved.getCreatedAt())
                .build();

        return commentDto;

    }

    public CommentDto comment_edit(UserCommentRequest userCommentRequest, Long postId, Long id, String name){

        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException(ErrorCode.POST_NOT_FOUND, "해당 포스트가 없습니다."));
        User user = userRepository.findByUserName(name).orElseThrow(()-> new UserException(ErrorCode.USERNAME_NOT_FOUND,"Not founded")); // 인증 실패
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new UserException(ErrorCode.COMMENT_NOT_FOUND,"해당 댓글이 없습니다.")); // 댓글 불일치

        if(!(comment.getUser().getUserName().equals(name)))  throw new UserException(ErrorCode.INVALID_PERMISSION, "사용자가 권한이 없습니다."); // 작성자 불일치


        comment.setId(id);
        comment.setComment(userCommentRequest.getComment());
        comment.setPost(post);
        comment.setUser(user);
        comment.setLastModifiedAt(LocalDateTime.now());

        Comment saved = commentRepository.saveAndFlush(comment);

        CommentDto commentDto = CommentDto.builder()
                .id(saved.getId())
                .comment(saved.getComment())
                .userName(saved.getUser().getUserName())
                .postId(saved.getPost().getId())
                .createdAt(saved.getCreatedAt())
                .lastModifiedAt(saved.getLastModifiedAt())
                .build();

        return commentDto;
    }


    public CommentDto comment_delete(Long postId, Long id, String name){

        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException(ErrorCode.POST_NOT_FOUND, "해당 포스트가 없습니다."));
        User user = userRepository.findByUserName(name).orElseThrow(()-> new UserException(ErrorCode.USERNAME_NOT_FOUND,"Not founded")); // 인증 실패
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new UserException(ErrorCode.COMMENT_NOT_FOUND,"해당 댓글이 없습니다.")); // 댓글 불일치

        if(!(comment.getUser().getUserName().equals(name)))  throw new UserException(ErrorCode.INVALID_PERMISSION, "사용자가 권한이 없습니다."); // 작성자 불일치

        commentRepository.delete(comment);

        CommentDto commentDto = CommentDto.builder()
                .id(id)
                .build();

        return commentDto;
    }


    public List<UserCommentResponse> comment_list(Long postId, PageRequest pageRequest){
        Post post = postRepository.findById(postId).orElseThrow(()-> new UserException(ErrorCode.POST_NOT_FOUND, "해당 포스트가 없습니다."));
        Page<Comment> list = commentRepository.findAll(pageRequest);

        List<UserCommentResponse> commentList = list.map(lists -> UserCommentResponse.builder()
                .id(lists.getId())
                .comment(lists.getComment())
                .userName(lists.getUser().getUserName())
                .postId(lists.getPost().getId())
                .createdAt(lists.getCreatedAt())
                .build())
                .toList();

        return commentList;
    }

    public List<UserPostMyResponse> post_my(String name, PageRequest pageRequest){
        User user = userRepository.findByUserName(name).orElseThrow(()-> new UserException(ErrorCode.USERNAME_NOT_FOUND,"Not founded"));
        Page<Post> list = postRepository.findByUserId(user.getId(), pageRequest);

        List<UserPostMyResponse> postList = list.map(lists -> UserPostMyResponse.builder()
                        .id(lists.getId())
                        .title(lists.getTitle())
                        .body(lists.getBody())
                        .userName(lists.getUser().getUserName())
                        .createdAt(lists.getCreatedAt())
                        .build()).toList();

        return postList;
    }

    public Response like(Long postId, String name){

        Post post = postRepository.findById(postId).orElseThrow(()-> new UserException(ErrorCode.POST_NOT_FOUND, "해당 포스트가 없습니다."));
        User user = userRepository.findByUserName(name).orElseThrow(()-> new UserException(ErrorCode.USERNAME_NOT_FOUND,"Not founded"));

        if(likeRepository.findByUserIdAndPostId(user.getId(),post.getId()).isPresent()) throw new UserException(ErrorCode.DUPLICATE_LIKE,"이미 좋아요를 누르셨습니다.");


        Like like = Like.builder()
                .post(post)
                .user(user)
                .build();

        Like saved = likeRepository.save(like);

        return new Response("SUCCESS","좋아요를 눌렀습니다.");
    }


}















