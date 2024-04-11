package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entities.Booking;
import com.example.hotelbooking.web.model.BookingListResponse;
import com.example.hotelbooking.web.model.BookingResponse;
import com.example.hotelbooking.web.model.InsertBooking;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(BookingMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE)
public interface BookingMapper {

    Booking requestToBooking(InsertBooking insertBooking);

    BookingResponse bookingToResponse(Booking booking);

    default BookingListResponse bookingListToResponseList(List<Booking> bookingList) {
        BookingListResponse response = new BookingListResponse();
        response.setBookings(
                bookingList.stream().map(booking -> bookingToResponse(booking)).toList()
        );

        return response;
    }
}
