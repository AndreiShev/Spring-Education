package com.example.hotelbooking.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertRoomRequest {
    @NotBlank(message = "Наименование комнаты не может быть пустым")
    private String name;

    @NotBlank(message = "Описание комнаты не может быть пустым")
    private String description;

    @NotNull(message = "Номер комнаты не может быть пустым")
    @Positive(message = "Номер комнаты не может быть отрицательным")
    private Short number;

    @NotNull(message = "Стоимость не может быть пустой")
    @Positive(message = "Стоимость не может быть отрицательной")
    private Double price;

    @NotNull(message = "Максимальное количество человек не может быть пустым")
    @Positive(message = "Максимальное количество человек не может быть отрицательным")
    private Short maximumNumberOfPeople;

    @NotNull(message = "Отель не можеть быть пустым")
    @Positive(message = "ID отеля не можеть быть отрицательным")
    private Long hotelId;

}
