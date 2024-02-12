package com.example.rest.web.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestGetAll {
    @NotNull(message = "Номер страницы не может быть пустым")
    @Positive(message = "Номер страницы не может быть меньше 0")
    private Integer pageNumber;

    @NotNull(message = "Кол-во объектов на странице не может быть пустым")
    @Range(min = 5, max = 20, message = "Кол-во объектов на странице должно быть между {min} и {max}")
    private Integer limit;
}
