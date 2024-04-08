package com.example.hotelbooking.repository;

import com.example.hotelbooking.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Hotel findHotelByTitle(String title);

    Page<Hotel> findAll(Pageable nextPage);

}
