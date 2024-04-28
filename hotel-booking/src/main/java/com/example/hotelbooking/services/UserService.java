package com.example.hotelbooking.services;

import com.example.hotelbooking.entities.RoleType;
import com.example.hotelbooking.entities.User;
import com.example.hotelbooking.entities.UserRole;

public interface UserService {

    User findByUsername(String username);

    User findById(Long id);

    User save(User user, UserRole userRole);

    User update(Long id, User user);

    void delete(Long id);
}
