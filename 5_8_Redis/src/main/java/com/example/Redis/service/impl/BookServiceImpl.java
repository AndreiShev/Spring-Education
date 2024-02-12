package com.example.Redis.service.impl;

import com.example.Redis.configuration.propeties.AppCacheProperties;
import com.example.Redis.errors.EntityNotFoundException;
import com.example.Redis.model.Book;
import com.example.Redis.model.Category;
import com.example.Redis.repository.BookRepository;
import com.example.Redis.repository.CategoryRepository;
import com.example.Redis.service.BookService;
import com.example.Redis.service.CategoryService;
import com.example.Redis.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    @Autowired
    private final CacheManager cacheManager;

    @Override
    @Cacheable(value = AppCacheProperties.CashNames.BOOk_BY_CATEGORY_NAME, key = "#categoryName")
    public List<Book> getAllByCategoryName(Integer offset, Integer limit, String categoryName) {
        return repository.findBookListByCategory(PageRequest.of(offset, limit), categoryName).getContent();
    }

    @Override
    public Book findById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Книга с Id {0} не найдена", id))
        );
    }

    @Override
    @Cacheable(value = AppCacheProperties.CashNames.BOOK_BY_NAME_AND_AUTHOR, key = "#name + #author")
    public Book getByNameAndAuthor(String name, String author) {
        return repository.findBookByNameAndAuthor(name, author).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Книга с именем {0} и автором {1} не найдена", name, author))
        );
    }

    /**
     * Реализуйте в сервисном слое методы создания, обновления и удаления книги. Эти методы должны инвалидировать кеши, которые заполняются при поиске сущности.
     * Инвалидация кешей должна происходить по их ключу.
     * */

    @Override
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CashNames.BOOk_BY_CATEGORY_NAME, key = "#book.category.name"),
            @CacheEvict(value = AppCacheProperties.CashNames.BOOK_BY_NAME_AND_AUTHOR, key = "#book.name + #book.author")
    })
    public Book save(Book book) {
        Category savedCategory = categoryService.save(book.getCategory());
        book.setCategory(savedCategory);
        Book savedBook = repository.save(book);

        return savedBook;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CashNames.BOOk_BY_CATEGORY_NAME, key = "#book.category.name"),
            @CacheEvict(value = AppCacheProperties.CashNames.BOOK_BY_NAME_AND_AUTHOR, key = "#book.name + #book.author")
    })
    public Book update(Book book) {
        Book existedBook = findById(book.getId());
        Category category = existedBook.getCategory();
        if (book.getCategory().getName() != category.getName()) {
            category.setName(book.getCategory().getName());
            book.setCategory(categoryRepository.save(category));
        }

        BeanUtils.copyNonNullProperties(book, existedBook);
        return repository.save(existedBook);
    }

    @Override
    public void delete(Integer id) {
        Book book = findById(id);
        repository.deleteById(id);

        cacheManager.getCache(AppCacheProperties.CashNames.BOOK_BY_NAME_AND_AUTHOR)
                .evict(book.getName()+book.getAuthor());
        cacheManager.getCache(AppCacheProperties.CashNames.BOOk_BY_CATEGORY_NAME)
                .evict(book.getCategory().getName());
    }
}
