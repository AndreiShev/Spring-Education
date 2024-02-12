package com.example.rest.services;

import com.example.rest.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {
    List<User> findAll(Integer offset, Integer limit);

    User findById(Long id);

    User save(User user);

    User update(User user);

    void deleteById(Long id);

    //User saveWithOrders(User user, List<Order> orders);
}
