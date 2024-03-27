package com.example.rest.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertNewsRequest {
    @NotNull(message = "Категория новостей должна быть заполнена")
    @Positive(message = "ID категории новостей должно быть положительно")
    private Integer newsCategoryId;

    @NotBlank(message = "Описание новостей должно быть заполнено")
    @Size(min = 50, max = 10000, message = "Описание новости должно содержать от {min} до {max} слов")
    private String description;
}
