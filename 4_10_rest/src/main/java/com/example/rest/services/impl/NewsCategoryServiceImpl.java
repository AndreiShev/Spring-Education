package com.example.rest.services.impl;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.model.NewsCategory;
import com.example.rest.repository.CommentRepository;
import com.example.rest.repository.NewsCategoryRepository;
import com.example.rest.services.NewsCategoryService;
import com.example.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsCategoryServiceImpl implements NewsCategoryService {
    private final NewsCategoryRepository newsCategoryRepository;

    @Override
    public List<NewsCategory> findAll(Integer offset, Integer limit) {
        return newsCategoryRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    @Override
    public NewsCategory findById(Integer id) {
        return newsCategoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("News Category not found by id {0}", id))
        );
    }

    @Override
    public NewsCategory save(NewsCategory newsCategory) {
        return newsCategoryRepository.save(newsCategory);
    }

    @Override
    public NewsCategory update(NewsCategory newsCategory) {
        NewsCategory existedCategory = findById(newsCategory.getId());
        BeanUtils.copyNonNullProperties(newsCategory, existedCategory);

        return newsCategoryRepository.save(newsCategory);
    }

    @Override
    public void deleteById(Integer id) {
        newsCategoryRepository.deleteById(id);
    }
}
