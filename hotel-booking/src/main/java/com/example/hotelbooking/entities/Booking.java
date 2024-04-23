package com.example.hotelbooking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bookingFrom;

    private LocalDateTime bookingTo;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User renter;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "booking_room",
            joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    private List<Room> rooms = new ArrayList<>();
}
