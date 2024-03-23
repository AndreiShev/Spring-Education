package com.example.rest.web.controller;

import com.example.rest.mapper.NewsMapper;
import com.example.rest.model.News;
import com.example.rest.services.NewsService;
import com.example.rest.web.model.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/news")
public class NewsController {
    private final NewsMapper newsMapper;
    private final NewsService newsServiceImpl;


    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(@RequestBody @Valid NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        newsServiceImpl.findAll(filter)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                newsMapper.newsToResponse(
                        newsServiceImpl.findById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        News news = newsServiceImpl.save(newsMapper.requestToNews(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(news));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId,
                                                       @RequestBody @Valid UpsertNewsRequest request) {
        News updatedNews = newsServiceImpl.update(newsMapper.requestToNews(newsId, request));

        return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
    }

    @DeleteMapping(path = "/", params = {"id", "userId"})
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        newsServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
