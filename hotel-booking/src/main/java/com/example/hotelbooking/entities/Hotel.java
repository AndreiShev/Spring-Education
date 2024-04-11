package com.example.hotelbooking.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String titleOfTheAd;
    private String city;
    private String address;
    private Double distanceFromCityCenter;
    private Double rating;
    private Long numberOfRatings;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    private Set<Room> rooms;
}
