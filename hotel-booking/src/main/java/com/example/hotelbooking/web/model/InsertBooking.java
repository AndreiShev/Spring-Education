package com.example.hotelbooking.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertBooking {

    @NotBlank(message = "Начало брони не может быть пустым")
    private String bookingFrom;

    @NotBlank(message = "Окончание брони не может быть пустым")
    private String bookingTo;

    @NotNull(message = "ID пользователя не может быть пустым")
    @Positive(message = "ID пользователя не может быть отрицательным")
    private Long userId;

    @NotNull(message = "Укажите забронированные комнаты")
    private List<Long> roomsId;
}