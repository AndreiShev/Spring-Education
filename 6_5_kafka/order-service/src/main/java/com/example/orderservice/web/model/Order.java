package com.example.orderservice.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @NotBlank(message = "Наименование продукта должно быть заполнено!")
    private String product;

    @Range(min = 1, max = 100000, message = "Количество должно быть в интервале от {min} до {max}")
    private Integer quantity;
}
