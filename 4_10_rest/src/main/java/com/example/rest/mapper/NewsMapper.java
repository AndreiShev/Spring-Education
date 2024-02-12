package com.example.rest.mapper;

import com.example.rest.model.News;
import com.example.rest.web.model.*;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE, uses = {UserMapper.class, CommentMapper.class})
public interface NewsMapper {

    News requestToNews(UpsertNewsRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpsertNewsRequest request);

    NewsResponse newsToResponse(News news);

    List<NewsResponseForResponseList> newsListToResponseList(List<News> news);

    default NewsListResponse newsListToNewsListResponse(List<News> news) {
        NewsListResponse response = new NewsListResponse();
        response.setNews(newsListToResponseList(news));

        return response;
    }
}
