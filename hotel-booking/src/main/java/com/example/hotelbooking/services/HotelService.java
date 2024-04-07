package com.example.hotelbooking.services;

import com.example.hotelbooking.entities.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> getAllHotel(Integer offset, Integer limit);
    Hotel getHotelById(Long id);

    Hotel save(Hotel hotel);

    Hotel update(Long hotelId, Hotel hotel);

    void delete(Long id);
}
