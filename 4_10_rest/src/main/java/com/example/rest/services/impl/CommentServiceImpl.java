package com.example.rest.services.impl;

import com.example.rest.aop.CheckWhoEditingComment;
import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.model.Comment;
import com.example.rest.model.User;
import com.example.rest.repository.CommentRepository;
import com.example.rest.services.CommentService;
import com.example.rest.services.UserService;
import com.example.rest.utils.BeanUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Comment with id {0} not found", id))
        );
    }

    @Override
    public Comment save(Comment comment) {
        User user = userService.findById(comment.getUser().getId());
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    @Override
    @CheckWhoEditingComment
    public Comment update(Comment comment) {
        User user = userService.findById(comment.getUser().getId());
        comment.setUser(user);
        Comment existComment = findById(comment.getId());
        BeanUtils.copyNonNullProperties(comment, existComment);
        existComment.setUser(user);

        return commentRepository.save(existComment);
    }

    @Override
    @CheckWhoEditingComment
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
