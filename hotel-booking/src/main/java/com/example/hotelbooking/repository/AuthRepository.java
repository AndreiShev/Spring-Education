package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.AuthEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends MongoRepository<AuthEvent, String> {

}
