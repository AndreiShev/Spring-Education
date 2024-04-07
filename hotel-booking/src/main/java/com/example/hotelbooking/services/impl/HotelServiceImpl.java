package com.example.hotelbooking.services.impl;

import com.example.hotelbooking.entities.Hotel;
import com.example.hotelbooking.exception.EntityNotFoundException;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.services.HotelService;
import com.example.hotelbooking.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;

    @Override
    public List<Hotel> getAllHotel(Integer offset, Integer limit) {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Отель с таким ID {0} не найден", id))
        );
    }

    @Override
    public Hotel save(Hotel hotel) {
        Hotel savedHotel = hotelRepository.save(hotel);
        return savedHotel;
    }

    @Override
    public Hotel update(Long hotelId, Hotel hotel) {
        Hotel existedHotel = getHotelById(hotelId);
        Utils.copyNonNullValues(hotel, existedHotel);
        return hotelRepository.save(existedHotel);
    }

    @Override
    public void delete(Long id) {
        hotelRepository.deleteById(id);
    }
}
