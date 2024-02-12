package com.example.Redis.web.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestGetListByCategory {
    @NotNull(message = "Укажите страницу")
    @Min(value = 0, message = "Номер страницы должен быть положительным")
    private Integer offset;

    @NotNull(message = "Укажите количество книг на странице")
    @Range(min = 1, max = 50, message = "Количество книг на странице долэно быть в интервале от {min} до {max}")
    private Integer limit;

    @NotBlank(message = "Категория книги должна быть заполнена")
    private String categoryName;
}
