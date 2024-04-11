package com.example.hotelbooking.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertBooking {

    private LocalDateTime bookingFrom;

    private LocalDateTime bookingTo;

    private Long userId;

    private List<Long> roomsId;
}