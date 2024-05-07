package com.example.hotelbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "booking_events")
@Builder
public class BookingEvent {
    @Id
    private String id;

    private Long bookingUser;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;
}
