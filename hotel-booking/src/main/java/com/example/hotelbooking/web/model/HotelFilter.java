package com.example.hotelbooking.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelFilter {
    @NotNull(message = "Номер страницы не может быть пустым")
    @Range(min = 0, message = "Номер страницы не может быть меньше 0")
    private Integer pageNumber;

    @NotNull(message = "Кол-во объектов на странице не может быть пустым")
    @Range(min = 5, max = 20, message = "Кол-во объектов на странице должно быть между {min} и {max}")
    private Integer pageSize;

    @Positive(message = "Стоимость не может быть отрицательной")
    private Long id;

    private String title;

    private String titleOfTheAd;

    private String city;

    private String address;

    @Positive(message = "Расстояние до центра не может быть отрицательным")
    private Double distanceFromCityCenter;

    @Positive(message = "Рейтинг не может быть отрицательным")
    private Double rating;

    @Positive(message = "Количество оценок не может быть отрицательным")
    private Long numberOfRatings;
}
