package com.example.hotelbooking.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertRoomRequest {
    private String name;

    private String description;

    private Short number;

    private Double price;

    private Short maximumNumberOfPeople;

    private LocalDateTime bookingFrom;

    private LocalDateTime bookingTo;

    private Long hotel;

}
