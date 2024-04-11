package com.example.hotelbooking.services.impl;

import com.example.hotelbooking.entities.Booking;
import com.example.hotelbooking.entities.Room;
import com.example.hotelbooking.exception.BookingException;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.services.BookingService;
import com.example.hotelbooking.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    @Override
    public Booking create(Booking booking) {
        List<Room> rooms = new ArrayList<>();
        rooms.addAll(booking.getRooms());
        if (checkRoomDates(booking)) {
            booking.getRooms().clear();
            Booking savedBooking = bookingRepository.save(booking);
            savedBooking.getRooms().addAll(rooms);
            return bookingRepository.save(savedBooking);
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
            bookings.addAll(bookingRepository.getBookingsAfterToday(item.getId()
                    , LocalDateTime.now()
                    , item.getBookingFrom()
                    , item.getBookingTo()));
        }

        if (bookings.size() == 0) {
            return true;
        }

        return false;
    }

    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }
}
