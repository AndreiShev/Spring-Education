package com.example.Redis.service;

import com.example.Redis.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getById(Integer id);

    Category save(Category category);

    Category update(Category category);

    void delete(Integer id);
}
