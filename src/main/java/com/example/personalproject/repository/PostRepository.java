package com.example.personalproject.repository;

import com.example.personalproject.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository <Post, Long> {
    void deleteById (Long id);
}
