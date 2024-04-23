package com.example.hotelbooking.repository;

import com.example.hotelbooking.entities.Booking;
import com.example.hotelbooking.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    //@Query("SELECT Booking FROM Booking b JOIN b.rooms r WHERE b.bookingTo > ?2 and r in ?1 and (?3 between b.bookingFrom and b.bookingTo or ?4 between b.bookingFrom and b.bookingTo)")
    @Query("SELECT b FROM Booking b WHERE b.bookingTo > ?1")
    public List<Booking> getBookingsAfterToday(LocalDateTime now);

    @Query("SELECT b FROM Booking b JOIN b.rooms r WHERE b.bookingTo > ?2 and r.id in ?1 and (?3 between b.bookingFrom and b.bookingTo or ?4 between b.bookingFrom and b.bookingTo)")
    List<Booking> getBookingsAfterToday(List<Long> room, LocalDateTime now, LocalDateTime from, LocalDateTime to);
}
