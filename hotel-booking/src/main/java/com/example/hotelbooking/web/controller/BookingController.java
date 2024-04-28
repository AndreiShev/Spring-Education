package com.example.hotelbooking.web.controller;

import com.example.hotelbooking.mapper.BookingMapper;
import com.example.hotelbooking.services.BookingService;
import com.example.hotelbooking.web.model.BookingListResponse;
import com.example.hotelbooking.web.model.BookingResponse;
import com.example.hotelbooking.web.model.InsertBooking;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody @Valid InsertBooking request) {
       return ResponseEntity.status(HttpStatus.CREATED).body(
               bookingMapper.bookingToResponse(bookingService.create(bookingMapper.requestToBooking(request)))
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
}
