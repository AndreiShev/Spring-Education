package com.example.hotelbooking.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id;

    private LocalDateTime bookingFrom;

    private LocalDateTime bookingTo;

    private String username;

    private List<String> roomsName = new ArrayList<>();

}
