package com.example.rest.aop;

import com.example.rest.exception.ObjectCantChangeException;
import com.example.rest.model.News;
import com.example.rest.services.NewsService;
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
public class CheckWhoEditingNewsAspect {
    private final NewsService newsService;

    @Before(value = "execution(* com.example.rest.services.NewsService.update(*)) && @annotation(CheckWhoEditingNews)")
    public void checkBeforeUpdate(final JoinPoint joinPoint) {
        News news = (News) joinPoint.getArgs()[0];
        News existingNews = newsService.findById(news.getId());
        if (news.getUser().getId() != existingNews.getUser().getId()) {
            throw new ObjectCantChangeException("Новость может быть обновлена только создавшим ее пользователем");
        }
    }

    @Before("execution(* com.example.rest.services.NewsService.deleteById(*)) && @annotation(CheckWhoEditingNews)")
    public void checkBeforeDelete() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        News existingNews = newsService.findById(Long.valueOf(request.getParameter("id")));
        Long userId = Long.valueOf(request.getParameter("userId"));

        if (existingNews.getUser().getId() != userId) {
            throw new ObjectCantChangeException("Новость может быть удалена только создавшим ее пользователем");
        }
    }
}
