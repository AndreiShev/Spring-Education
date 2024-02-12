package com.example.rest.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertNewsCategoryRequest {
    @NotBlank(message = "Наименвоание категории новостей должно быть заполнено!")
    @Size(min = 1, max = 5, message = "Наименование категории новостей не может быть меньше {min} и больше {max}")
    private String name;

    @NotBlank(message = "Описание категории нвовостей должн быть заполнено")
    @Size(min = 2, max = 50, message = "Описание категории должно содержать от {min} до {max} слов")
    private String description;
}
