package com.example.hotelbooking.services.impl;

import com.example.hotelbooking.entities.Hotel;
import com.example.hotelbooking.exception.EntityNotFoundException;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.repository.HotelSpecification;
import com.example.hotelbooking.services.HotelService;
import com.example.hotelbooking.utils.Utils;
import com.example.hotelbooking.web.model.HotelFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;


    @Override
    public Hotel changeRating(Long id, Integer newMark) {
        if (newMark < 1 || newMark >5) {
            throw new IllegalArgumentException("Оценка должна быть от 1 до 5");
        }

        Hotel hotel = getHotelById(id);

        if (hotel.getNumberOfRatings() == 0) {
            hotel.setRating((double) newMark);
        } else {
            Double totalRating = (hotel.getNumberOfRatings() * hotel.getRating()) - hotel.getRating() + newMark;
            hotel.setRating(totalRating / hotel.getNumberOfRatings());
        }

        hotel.setNumberOfRatings(hotel.getNumberOfRatings()+1);
        return save(hotel);
    }

    @Override
    public List<Hotel> getAllHotel(HotelFilter filter) {
        return hotelRepository.findAll(
                HotelSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent();
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Отель с таким ID {0} не найден", id))
        );
    }

    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
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
