package com.example.rest.services;

import com.example.rest.model.NewsCategory;
import java.util.List;


public interface NewsCategoryService {
    List<NewsCategory> findAll(Integer offset, Integer limit);

    NewsCategory findById(Integer id);

    NewsCategory save(NewsCategory newsCategory);

    NewsCategory update(NewsCategory newsCategory);

    void deleteById(Integer id);
}
