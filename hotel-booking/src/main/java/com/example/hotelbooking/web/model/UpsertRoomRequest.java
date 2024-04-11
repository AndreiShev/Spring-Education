package com.example.hotelbooking.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertRoomRequest {
    private String name;

    private String description;

    private Short number;

    private Double price;

    private Short maximumNumberOfPeople;

    private Long hotelId;

}
