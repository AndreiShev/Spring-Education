package com.example.hotelbooking.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertHotelRequest {
    private String title;
    private String titleOfTheAd;
    private String city;
    private String address;
    private Double distanceFromCityCenter;
}
