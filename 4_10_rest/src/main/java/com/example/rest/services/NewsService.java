package com.example.rest.services;

import com.example.rest.model.News;
import com.example.rest.web.model.NewsFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    List<News> findAll(NewsFilter filter);

    News findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);
}
