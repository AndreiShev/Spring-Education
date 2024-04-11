package com.example.hotelbooking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Short number;

    private Double price;

    private Short maximumNumberOfPeople;

    private LocalDateTime bookingFrom;

    private LocalDateTime bookingTo;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @ManyToMany(mappedBy = "rooms", fetch = FetchType.EAGER)
    private List<Booking> bookings;

}
