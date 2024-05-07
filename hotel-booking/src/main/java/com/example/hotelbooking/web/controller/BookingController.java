package com.example.hotelbooking.web.controller;

import com.example.hotelbooking.entities.Booking;
import com.example.hotelbooking.mapper.BookingMapper;
import com.example.hotelbooking.model.AuthEvent;
import com.example.hotelbooking.model.BookingEvent;
import com.example.hotelbooking.services.BookingService;
import com.example.hotelbooking.web.model.BookingListResponse;
import com.example.hotelbooking.web.model.BookingResponse;
import com.example.hotelbooking.web.model.InsertBooking;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final BookingMapper bookingMapper;

    @Value("${app.kafka.kafkaBookingTopic}")
    private String topicName;

    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody @Valid InsertBooking request) {
        Booking booking = bookingService.create(bookingMapper.requestToBooking(request));
        kafkaTemplate.send(topicName, getBooking(booking));

        return ResponseEntity.status(HttpStatus.CREATED).body(
               bookingMapper.bookingToResponse(booking)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<BookingListResponse> getAll() {
        return ResponseEntity.ok(bookingMapper.bookingListToResponseList(bookingService.getAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private BookingEvent getBooking(Booking booking) {
        BookingEvent bookingEvent = new BookingEvent();
        bookingEvent.setBookingUser(booking.getRenter().getId());
        bookingEvent.setCheckInDate(booking.getBookingFrom());
        bookingEvent.setCheckOutDate(booking.getBookingTo());

        return bookingEvent;
    }
}
