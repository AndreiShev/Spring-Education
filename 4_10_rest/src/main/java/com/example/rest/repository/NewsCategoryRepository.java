package com.example.rest.repository;

import com.example.rest.model.NewsCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Integer> {
    Page<NewsCategory> findAll(Pageable nextPage);
}
