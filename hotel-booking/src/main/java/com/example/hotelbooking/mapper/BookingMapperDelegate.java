package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entities.Booking;
import com.example.hotelbooking.services.RoomService;
import com.example.hotelbooking.services.UserService;
import com.example.hotelbooking.web.model.BookingResponse;
import com.example.hotelbooking.web.model.InsertBooking;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


public abstract class BookingMapperDelegate implements BookingMapper {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Override
    public BookingResponse bookingToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setBookingFrom(booking.getBookingFrom());
        response.setBookingTo(booking.getBookingTo());
        response.setUsername(booking.getRenter().getUsername());
        response.getRoomsName().addAll(booking.getRooms().stream().map(room -> room.getName()).toList());
        //response.setRoomsName(new ArrayList<>());

        return response;
    }

    @Override
    public Booking requestToBooking(InsertBooking insertBooking) {
        Booking booking = new Booking();
        booking.setRenter(userService.findById(insertBooking.getUserId()));
        booking.setBookingFrom(insertBooking.getBookingFrom());
        booking.setBookingTo(insertBooking.getBookingTo());
        booking.getRooms().addAll(insertBooking.getRoomsId().stream().map(room -> roomService.getById(room)).toList());
        return booking;
    }
}
