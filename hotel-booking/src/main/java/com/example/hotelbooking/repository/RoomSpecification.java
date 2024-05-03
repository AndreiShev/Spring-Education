package com.example.hotelbooking.repository;

import com.example.hotelbooking.entities.Booking;
import com.example.hotelbooking.entities.Room;
import com.example.hotelbooking.web.model.RoomFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public interface RoomSpecification {
    static Specification<Room> withFilter(RoomFilter filter) {
        return Specification
                .where(byRoomId(filter.getRoomId()))
                .and(byName(filter.getName()))
                .and(byMinPrice(filter.getMinPrice()))
                .and(byMaxPrice(filter.getMaxPrice()))
                .and(byNumberOfPeople(filter.getNumberOfPeople()))
                .and(byDateFromTo(filter.getBookingFrom(), filter.getBookingTo()))
                .and(byHotelId(filter.getHotelId()));
    }

    static Specification<Room> byRoomId(Long roomId) {
        return (root, query, criteriaBuilder) -> {
            if (roomId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("id"), roomId);
        };
    }

    static Specification<Room> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    static Specification<Room> byMinPrice(Double minPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }

    static Specification<Room> byMaxPrice(Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (maxPrice == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }

    static Specification<Room> byNumberOfPeople(Short numberOfPeople) {
        return (root, query, criteriaBuilder) -> {
            if (numberOfPeople == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("maximumNumberOfPeople"), numberOfPeople);
        };
    }

    static Specification<Room> byDateFromTo(LocalDateTime bookingFrom, LocalDateTime bookingTo) {
        return (root, query, criteriaBuilder) -> {
            Join<Room, Booking> bookings = root.join("bookings", JoinType.LEFT);
            Predicate to = criteriaBuilder.greaterThan(bookings.get("bookingTo"), bookingFrom).not();
            Predicate from = criteriaBuilder.lessThan(bookings.get("bookingFrom"), bookingTo).not();
            Predicate nullValue = criteriaBuilder.isNull(bookings.get("id"));
            return criteriaBuilder.or(nullValue, to, from);
        };
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return (root, query, criteriaBuilder) -> {
            if (hotelId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("hotel").get("id"), hotelId);
        };
    }


}
