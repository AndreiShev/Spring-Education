package com.example.rest.aop;


import com.example.rest.exception.ObjectCantChangeException;
import com.example.rest.model.Comment;
import com.example.rest.model.RoleType;
import com.example.rest.services.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUsername() != existingComment.getUser().getUsername()) {
            throw new ObjectCantChangeException("Комментарий может быть обновлен только создавшим его пользователем");
        }
    }

    @Before("execution(* com.example.rest.services.CommentService.deleteById(*)) && @annotation(CheckWhoEditingComment)")
    public void checkBeforeDelete(final JoinPoint joinPoint) {
        Long commentId = (Long) joinPoint.getArgs()[0];
        Comment existingComment = commentService.findById(commentId);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = getRoles(userDetails);

        if ((existingComment.getUser().getUsername() != userDetails.getUsername()) && !containsAdvancedPermissions(roles)) {
            throw new ObjectCantChangeException("Комментарий может быть удален только создавшим его пользователем");
        }
    }

    private List<String> getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream().map(grantedAuthority ->
                grantedAuthority.getAuthority()).collect(Collectors.toList());
    }

    private boolean containsAdvancedPermissions(List<String> roles) {
        List<String> roleList = new ArrayList<>();
        roleList.add(RoleType.ROLE_ADMIN.name());
        roleList.add(RoleType.ROLE_MODERATOR.name());
        for (String role: roleList) {
            if (roles.contains(role)) {
                return true;
            }
        }

        return false;
    }

}
