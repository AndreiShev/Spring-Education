package com.example.Redis.service;

import com.example.Redis.model.Book;

import java.util.List;

public interface BookService {

    /*
    * Реализуйте эндпоинты, которые позволяют:
найти одну книгу по её названию и автору,
найти список книг по имени категории,
создать книгу,
обновить информацию о книге,
удалить книгу по ID.*/

    List<Book> getAllByCategoryName(Integer offset, Integer limit, String categoryName);

    Book findById(Integer id);

    Book getByNameAndAuthor(String name, String author);

    Book save(Book book);

    Book update(Book book);

    void delete(Integer id);
}
