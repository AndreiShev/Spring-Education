package com.example.rest.services.impl;

import com.example.rest.aop.CheckWhoEditingNews;
import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.model.News;
import com.example.rest.model.NewsCategory;
import com.example.rest.model.User;
import com.example.rest.repository.NewsRepository;
import com.example.rest.repository.NewsSpecification;
import com.example.rest.services.NewsCategoryService;
import com.example.rest.services.NewsService;
import com.example.rest.services.UserService;
import com.example.rest.utils.BeanUtils;
import com.example.rest.web.model.NewsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserService userService;
    private final NewsCategoryService newsCategoryService;

    @Override
    public List<News> findAll(NewsFilter filter) {
        return newsRepository.findAll(
                NewsSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("News not found by id {0}", id))
        );
    }

    @Override
    public News save(News news) {
        User user = userService.findById(news.getUser().getId());
        NewsCategory newsCategory = newsCategoryService.findById(news.getNewsCategory().getId());
        news.setNewsCategory(newsCategory);
        news.setUser(user);

        return newsRepository.save(news);
    }

    @Override
    @CheckWhoEditingNews
    public News update(News news) {
        User user = userService.findById(news.getUser().getId());
        NewsCategory newsCategory = newsCategoryService.findById(news.getNewsCategory().getId());
        news.setNewsCategory(newsCategory);
        news.setUser(user);
        News existNew = findById(news.getId());
        BeanUtils.copyNonNullProperties(news, existNew);

        return newsRepository.save(existNew);
    }

    @Override
    @CheckWhoEditingNews
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
