package com.example.hotelbooking.repository;

import com.example.hotelbooking.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT Booking FROM Booking b JOIN b.rooms r WHERE b.bookingTo > :now and :room = r.id and (:from between b.bookingFrom and b.bookingTo or :to between b.bookingFrom and b.bookingTo)")
    List<Booking> getBookingsAfterToday(Long room, LocalDateTime now, LocalDateTime from, LocalDateTime to);
}
