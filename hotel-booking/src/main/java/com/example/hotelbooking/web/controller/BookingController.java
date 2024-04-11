package com.example.hotelbooking.web.controller;

import com.example.hotelbooking.mapper.BookingMapper;
import com.example.hotelbooking.services.BookingService;
import com.example.hotelbooking.web.model.BookingListResponse;
import com.example.hotelbooking.web.model.BookingResponse;
import com.example.hotelbooking.web.model.InsertBooking;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestParam InsertBooking request) {
       return ResponseEntity.status(HttpStatus.CREATED).body(
               bookingMapper.bookingToResponse(bookingService.create(bookingMapper.requestToBooking(request)))
       );
    }

    @GetMapping
    public ResponseEntity<BookingListResponse> getAll() {
        return ResponseEntity.ok(bookingMapper.bookingListToResponseList(bookingService.getAll()));
    }
}
