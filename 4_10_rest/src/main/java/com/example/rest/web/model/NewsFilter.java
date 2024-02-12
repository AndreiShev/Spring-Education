package com.example.rest.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
public class NewsFilter {
    @NotNull(message = "Номер страницы не может быть пустым")
    @Positive(message = "Номер страницы не может быть меньше 0")
    private Integer pageNumber;

    @NotNull(message = "Кол-во объектов на странице не может быть пустым")
    @Range(min = 5, max = 20, message = "Кол-во объектов на странице должно быть между {min} и {max}")
    private Integer pageSize;

    @NotBlank(message = "Категория новостей должна быть заполнена")
    private String category;

    @NotNull(message = "Автор новости должен быть заполнен")
    @Positive(message = "ID автора новости должно быть положительно")
    private Long user;
}
