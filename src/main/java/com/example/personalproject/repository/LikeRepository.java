package com.example.personalproject.repository;

import com.example.personalproject.domain.entity.Like;
import com.example.personalproject.domain.entity.Post;
import com.example.personalproject.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByUserIdAndPostId(Long id, Long postId);
}
