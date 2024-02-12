package com.example.Redis.service.impl;

import com.example.Redis.model.Category;
import com.example.Redis.repository.CategoryRepository;
import com.example.Redis.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public Category getById(Integer id) {
        return null;
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }
}
