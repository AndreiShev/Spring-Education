package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entities.Hotel;
import com.example.hotelbooking.utils.Utils;
import com.example.hotelbooking.web.model.HotelResponse;

public abstract class HotelMapperDelegate implements HotelMapper {

    @Override
    public HotelResponse hotelToResponse(Hotel hotel) {
        HotelResponse response = new HotelResponse();
        response.setId(hotel.getId());
        response.setTitle(hotel.getTitle());
        response.setTitleOfTheAd(hotel.getTitleOfTheAd());
        response.setCity(hotel.getCity());
        response.setNumberOfRatings(hotel.getNumberOfRatings());
        response.setAddress(hotel.getAddress());
        response.setDistanceFromCityCenter(hotel.getDistanceFromCityCenter());
        response.setRating(Utils.round(hotel.getRating(), 1));

        return response;
    }
}
