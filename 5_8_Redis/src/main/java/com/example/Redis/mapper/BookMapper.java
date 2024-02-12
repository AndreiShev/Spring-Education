package com.example.Redis.mapper;

import com.example.Redis.model.Book;
import com.example.Redis.model.Category;
import com.example.Redis.web.model.BookListResponse;
import com.example.Redis.web.model.BookResponse;
import com.example.Redis.web.model.UpsertBookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    Book requestToBook(UpsertBookRequest request);

    @Mapping(source = "bookId", target = "id")
    Book requestToBook(Integer bookId, UpsertBookRequest request);

    BookResponse bookToResponse(Book book);

    default BookListResponse bookListToBookResponseList(List<Book> books) {
        BookListResponse response = new BookListResponse();

        response.setBooks(books.stream()
                .map(this::bookToResponse).collect(Collectors.toList()));

        return response;
    }

    default Category map(String value) {
        Category category = new Category();
        category.setName(value);
        return category;
    }
}
