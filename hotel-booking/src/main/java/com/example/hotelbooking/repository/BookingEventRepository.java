package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.BookingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingEventRepository extends MongoRepository<BookingEvent, String> {

}
