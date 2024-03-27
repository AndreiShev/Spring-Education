package com.example.rest.mapper;

import com.example.rest.model.News;
import com.example.rest.services.NewsCategoryService;
import com.example.rest.services.UserService;
import com.example.rest.web.model.NewsResponse;
import com.example.rest.web.model.NewsResponseForResponseList;
import com.example.rest.web.model.UpsertNewsRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private NewsCategoryService newsCategoryServiceImpl;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public News requestToNews(UpsertNewsRequest request) {
        News news = new News();
        news.setDescription(request.getDescription());
        news.setNewsCategory(newsCategoryServiceImpl.findById(request.getNewsCategoryId()));

        return news;
    }

    @Override
    public News requestToNews(Long newsId, UpsertNewsRequest request) {
        News news = requestToNews(request);
        news.setId(newsId);

        return news;
    }

    @Override
    public NewsResponse newsToResponse(News news) {
        NewsResponse response = new NewsResponse();
        response.setId(news.getId());
        response.setDescription(news.getDescription());
        response.setCommentResponseList(commentMapper.commentListToResponseList(news.getCommentList()));
        return response;
    }

    @Override
    public List<NewsResponseForResponseList> newsListToResponseList(List<News> news) {
        List<NewsResponseForResponseList> response = new ArrayList<>();
        for (News item: news) {
            NewsResponseForResponseList responseItem = new NewsResponseForResponseList();
            responseItem.setId(item.getId());
            responseItem.setDescription(item.getDescription());
            responseItem.setNumberOfComments(item.getCommentList().size());
            response.add(responseItem);
        }

        return response;
    }
}
