package com.sparta.springlv3.repository;

import com.sparta.springlv3.entity.Board;
import com.sparta.springlv3.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByCreatedAtDesc();
}
