package com.example.rest.mapper;

import com.example.rest.model.Comment;
import com.example.rest.services.NewsService;
import com.example.rest.services.UserService;
import com.example.rest.web.model.UpsertCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;



public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private NewsService newsServiceImpl;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setNews(newsServiceImpl.findById(request.getNewsId()));
        comment.setContent(request.getContent());

        return comment;
    }

    @Override
    public Comment requestToComment(Long commentId, UpsertCommentRequest request) {
        Comment comment = requestToComment(request);
        comment.setId(commentId);

        return comment;
    }
}
