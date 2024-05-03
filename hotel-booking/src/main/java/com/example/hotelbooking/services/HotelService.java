package com.example.hotelbooking.services;

import com.example.hotelbooking.entities.Hotel;
import com.example.hotelbooking.web.model.HotelFilter;

import java.util.List;

public interface HotelService {
    Hotel changeRating(Long id, Integer newMark);

    List<Hotel> getAllHotel(HotelFilter filter);
    Hotel getHotelById(Long id);

    Hotel save(Hotel hotel);

    Hotel update(Long hotelId, Hotel hotel);

    void delete(Long id);
}
