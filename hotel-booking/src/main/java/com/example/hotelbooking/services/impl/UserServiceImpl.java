package com.example.hotelbooking.services.impl;

import com.example.hotelbooking.aop.CheckExistUser;
import com.example.hotelbooking.entities.User;
import com.example.hotelbooking.exception.EntityNotFoundException;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.services.UserService;
import com.example.hotelbooking.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User findByUsername(String username) {
        return repository.findUserByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Пользователь с именем {0} не найден", username))
        );
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Пользователь с ID {0} не найден", id))
        );
    }

    @Override
    @CheckExistUser
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User existeduser = findById(id);
        Utils.copyNonNullValues(user, existeduser);
        return repository.save(existeduser);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id);
    }
}
