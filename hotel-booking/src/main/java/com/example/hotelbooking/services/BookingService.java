package com.example.hotelbooking.services;

import com.example.hotelbooking.entities.Booking;

import java.util.List;

public interface BookingService {

    Booking create(Booking booking);

    List<Booking> getAll();

    void delete(Long id);
}
