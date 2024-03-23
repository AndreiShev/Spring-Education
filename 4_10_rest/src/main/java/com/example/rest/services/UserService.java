package com.example.rest.services;

import com.example.rest.model.Role;
import com.example.rest.model.User;


import java.util.List;


public interface UserService {
    List<User> findAll(Integer offset, Integer limit);

    User findById(Long id);

    User save(User user, Role role);

    User update(User user);

    void deleteById(Long id);

    User findByUserName(String username);
}
