package com.example.rest.repository;

import com.example.rest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable nextPage);

    @EntityGraph(attributePaths = {"roles"})
    User findByUsername(String username);

}
