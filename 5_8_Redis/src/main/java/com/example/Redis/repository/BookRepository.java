package com.example.Redis.repository;

import com.example.Redis.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("from Book where category.name=?1")
    Page<Book> findBookListByCategory(Pageable pageable, String category);

    Optional<Book> findBookByNameAndAuthor(String name, String author);
}
