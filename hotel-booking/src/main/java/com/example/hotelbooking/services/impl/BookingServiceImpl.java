package com.example.hotelbooking.services.impl;

import com.example.hotelbooking.entities.Booking;
import com.example.hotelbooking.entities.Room;
import com.example.hotelbooking.exception.BookingException;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.services.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public Booking create(Booking booking) {
        if (checkRoomDates(booking)) {
            return bookingRepository.save(booking);
        }

        throw new BookingException(
                MessageFormat.format("Комнаты на даты {0} - {1} уже заняты",
                        booking.getBookingFrom(),
                        booking.getBookingTo())
        );
    }

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    private boolean checkRoomDates(Booking booking) {
        List<Booking> bookings = new ArrayList<>();

        for (Room item: booking.getRooms()) {
            bookings.addAll(bookingRepository.getBookingsAfterToday(item.getId(),
                    LocalDateTime.now(),
                    booking.getBookingFrom(),
                    booking.getBookingTo()));
        }

        if (bookings.size() == 0) {
            return true;
        }

        return false;
    }
}
