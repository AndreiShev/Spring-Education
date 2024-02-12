package com.example.rest.services;

import com.example.rest.aop.CheckWhoEditingComment;
import com.example.rest.model.Comment;
import java.util.List;


public interface CommentService {
    List<Comment> findAll();

    Comment findById(Long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void deleteById(Long id);
}
