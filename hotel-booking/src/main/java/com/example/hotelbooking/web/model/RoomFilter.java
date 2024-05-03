package com.example.hotelbooking.web.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomFilter {
    @NotNull(message = "Номер страницы не может быть пустым")
    @Range(min = 0, message = "Номер страницы не может быть меньше 0")
    private Integer pageNumber;

    @NotNull(message = "Кол-во объектов на странице не может быть пустым")
    @Range(min = 5, max = 20, message = "Кол-во объектов на странице должно быть между {min} и {max}")
    private Integer pageSize;

    private Long roomId;

    private String name;

    private Double minPrice;

    private Double maxPrice;

    private Short numberOfPeople;

    @NotNull(message = "Дата заезда не может быть пустой")
    private LocalDateTime bookingFrom;

    @NotNull(message = "Дата выезда не может быть пустой")
    private LocalDateTime bookingTo;

    private Long hotelId;

}
