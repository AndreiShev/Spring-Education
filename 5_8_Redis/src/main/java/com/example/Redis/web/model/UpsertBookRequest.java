package com.example.Redis.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertBookRequest {
    @NotBlank(message = "Наименование книги должно быть заполнено!")
    @Size(min = 2, max = 30, message = "Наименовани книги не может быть меньше {min} и больше {max}")
    private String name;

    @NotBlank(message = "Автор книги должен быть заполнен!")
    @Size(min = 2, max = 30, message = "Имя автора не может быть меньше {min} и больше {max}")
    private String author;

    @NotBlank(message = "Категория книги должна быть заполнена!")
    @Size(min = 2, max = 30, message = "Количество символов в категории не может быть меньше {min} и больше {max}")
    private String category;

}
