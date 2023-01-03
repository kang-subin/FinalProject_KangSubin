package com.example.personalproject.repository;

import com.example.personalproject.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository <Comment,Long> {

}
