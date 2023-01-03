package com.example.personalproject.repository;

import com.example.personalproject.domain.entity.Comment;
import com.example.personalproject.domain.entity.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository <Post, Long> {
    void deleteById (Long id);
    Page<Post> findByUserId (Long id, Pageable pageable);
}
