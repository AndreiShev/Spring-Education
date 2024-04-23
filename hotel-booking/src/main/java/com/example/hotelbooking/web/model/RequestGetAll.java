package com.example.hotelbooking.web.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestGetAll {
    @NotNull(message = "Номер страницы не может быть пустым")
    @Positive(message = "Номер страницы не может быть отрицательным")
    private Integer pageNumber;

    @NotNull(message = "Количество страниц не может быть пустым")
    @Positive(message = "Количество страниц не может быть отрицательным")
    private Integer limit;
}
