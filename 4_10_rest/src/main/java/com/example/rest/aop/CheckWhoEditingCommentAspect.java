package com.example.rest.aop;

import com.example.rest.exception.ObjectCantChangeException;
import com.example.rest.model.Comment;
import com.example.rest.model.News;
import com.example.rest.services.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class CheckWhoEditingCommentAspect {
    private final CommentService commentService;

    @Before(value = "execution(* com.example.rest.services.CommentService.update(*)) && @annotation(CheckWhoEditingComment)")
    public void checkBeforeUpdate(final JoinPoint joinPoint) {
        Comment comment = (Comment) joinPoint.getArgs()[0];
        Comment existingComment = commentService.findById(comment.getId());
        if (comment.getUser().getId() != existingComment.getUser().getId()) {
            throw new ObjectCantChangeException("Комментарий может быть обновлен только создавшим его пользователем");
        }
    }

    @Before("execution(* com.example.rest.services.CommentService.deleteById(*)) && @annotation(CheckWhoEditingComment)")
    public void checkBeforeDelete() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Comment existingComment = commentService.findById(Long.valueOf(request.getParameter("id")));
        Long userId = Long.valueOf(request.getParameter("userId"));

        if (existingComment.getUser().getId() != userId) {
            throw new ObjectCantChangeException("Комментарий может быть удален только создавшим его пользователем");
        }
    }
}
