package com.example.rest.mapper;

import com.example.rest.model.NewsCategory;
import com.example.rest.web.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE)
public interface NewsCategoryMapper {

    NewsCategory requestToNewsCategory(UpsertNewsCategoryRequest request);

    @Mapping(source = "userId", target = "id")
    NewsCategory requestToNewsCategory(Integer userId, UpsertNewsCategoryRequest request);

    NewsCategoryResponse newsCategoryToResponse(NewsCategory newsCategory);

    default NewsCategoryListResponse newsCategoryListToNewsCategoryResponseList(List<NewsCategory> categories) {
        NewsCategoryListResponse response = new NewsCategoryListResponse();

        response.setCategories(categories.stream()
                .map(this::newsCategoryToResponse).collect(Collectors.toList()));

        return response;
    }
}
