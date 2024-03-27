package com.example.rest.web.controller;

import com.example.rest.mapper.CommentMapper;
import com.example.rest.model.Comment;
import com.example.rest.services.CommentService;
import com.example.rest.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentMapper commentMapper;
    private final CommentService commentServiceImpl;
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                commentMapper.commentToResponse(
                        commentServiceImpl.findById(id)
                )
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        Comment newComment = commentServiceImpl.save(commentMapper.requestToComment(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToResponse(newComment));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId,
                                               @RequestBody @Valid UpsertCommentRequest request) {
        Comment updatedComment = commentServiceImpl.update(commentMapper.requestToComment(commentId, request));

        return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
