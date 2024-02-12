package com.example.Redis.web.controllers;

import com.example.Redis.mapper.BookMapper;
import com.example.Redis.model.Book;
import com.example.Redis.service.BookService;
import com.example.Redis.web.model.BookListResponse;
import com.example.Redis.web.model.BookResponse;
import com.example.Redis.web.model.RequestGetListByCategory;
import com.example.Redis.web.model.UpsertBookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookMapper bookMapper;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<BookListResponse> findAllByCategory(@RequestBody @Valid RequestGetListByCategory request) {
        return ResponseEntity.ok(
                bookMapper.bookListToBookResponseList(
                        bookService.getAllByCategoryName(request.getOffset(), request.getLimit(), request.getCategoryName())
                )
        );
    }

    @GetMapping(path = "/", params = {"name", "author"})
    public ResponseEntity<BookResponse> findByNameAndAuthor(@RequestParam @NotNull String name
                                                            , @RequestParam @NotNull String author) {
        return ResponseEntity.ok(
                bookMapper.bookToResponse(
                        bookService.getByNameAndAuthor(name, author)
                )
        );
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody @Valid UpsertBookRequest request) {
        Book book = bookService.save(bookMapper.requestToBook(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.bookToResponse(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable("id") Integer bookId,
                                               @RequestBody @Valid UpsertBookRequest request) {
        Book updatedBook = bookService.update(bookMapper.requestToBook(bookId, request));

        return ResponseEntity.ok(bookMapper.bookToResponse(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
