package com.example.rest.aop;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.exception.ObjectCantChangeException;
import com.example.rest.model.News;
import com.example.rest.model.RoleType;
import com.example.rest.model.User;
import com.example.rest.repository.UserRepository;
import com.example.rest.services.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class CheckWhoEditingNewsAspect {
    private final NewsService newsService;
    private final UserRepository userRepository;

    @Before(value = "execution(* com.example.rest.services.NewsService.update(*)) && @annotation(CheckWhoEditingNews)")
    public void checkBeforeUpdate(final JoinPoint joinPoint) {
        News news = (News) joinPoint.getArgs()[0];
        News existingNews = newsService.findById(news.getId());
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        if (user.getId() != existingNews.getUser().getId()) {
            throw new ObjectCantChangeException("Новость может быть обновлена только создавшим ее пользователем");
        }
    }

    @Before("execution(* com.example.rest.services.NewsService.deleteById(*)) && @annotation(CheckWhoEditingNews)")
    public void checkBeforeDelete(final JoinPoint joinPoint) {
        Long newsId = (Long) joinPoint.getArgs()[0];
        News existingNews = newsService.findById(newsId);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = getRoles(userDetails);

        if (roles.size() == 1 && roles.get(0).equals(RoleType.ROLE_USER)
                && !existingNews.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new ObjectCantChangeException("Новость может быть удалена только создавшим ее пользователем");
        }
    }

    private List<String> getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream().map(grantedAuthority ->
                grantedAuthority.getAuthority()).collect(Collectors.toList());
    }
}
