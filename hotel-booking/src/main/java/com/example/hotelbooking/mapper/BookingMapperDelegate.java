package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entities.Booking;
import com.example.hotelbooking.services.RoomService;
import com.example.hotelbooking.services.UserService;
import com.example.hotelbooking.web.model.InsertBooking;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BookingMapperDelegate implements BookingMapper {

    private final RoomService roomService;
    private final UserService userService;
    @Override
    public Booking requestToBooking(InsertBooking insertBooking) {
        Booking booking = new Booking();
        booking.setUser(userService.findById(insertBooking.getUserId()));
        booking.setBookingFrom(insertBooking.getBookingFrom());
        booking.setBookingTo(insertBooking.getBookingTo());
        booking.setRooms(insertBooking.getRooms().stream().map(room -> roomService.getById(room)).toList());
        return booking;
    }
}
