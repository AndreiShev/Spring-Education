package com.example.hotelbooking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "renter", fetch = FetchType.EAGER)
    private List<Booking> bookingList;
}
