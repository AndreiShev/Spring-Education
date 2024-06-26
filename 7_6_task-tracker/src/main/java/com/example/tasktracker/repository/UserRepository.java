package com.example.tasktracker.repository;

import com.example.tasktracker.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Flux<User> findAllByIdIn(Set<String> users);

    Mono<User> findByUsername(String username);
}
