package com.example.rest.web.controller;

import com.example.rest.mapper.NewsMapper;
import com.example.rest.model.News;
import com.example.rest.services.NewsService;
import com.example.rest.web.model.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/news")
public class NewsController {
    private final NewsMapper newsMapper;
    private final NewsService newsServiceImpl;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<NewsListResponse> findAll(@RequestBody @Valid NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        newsServiceImpl.findAll(filter)
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                newsMapper.newsToResponse(
                        newsServiceImpl.findById(id)
                )
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
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

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
