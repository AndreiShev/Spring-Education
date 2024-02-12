package com.example.Redis.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Integer id;

    private String name;

    private String author;

    private CategoryResponse category;
}
