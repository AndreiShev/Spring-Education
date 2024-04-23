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
public class UpsertHotelRequest {

    @NotBlank(message = "Название отеля не может быть пустым")
    private String title;

    @NotBlank(message = "Слоган отеля не может быть пустым")
    private String titleOfTheAd;

    @NotBlank(message = "Город отеля не может быть пустым")
    private String city;

    @NotBlank(message = "Адрес отеля не может быть пустым")
    private String address;

    @NotNull(message = "Расстояние до центра не может быть пустым")
    @Positive(message = "Расстояние до центра не может быть отрицательным")
    private Double distanceFromCityCenter;
}
