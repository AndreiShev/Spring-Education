package com.example.hotelbooking.services;

import com.example.hotelbooking.entities.User;

public interface UserService {

    User findByUsername(String username);

    User findById(Long id);

    User save(User user);

    User update(Long id, User user);

    void delete(Long id);
}
