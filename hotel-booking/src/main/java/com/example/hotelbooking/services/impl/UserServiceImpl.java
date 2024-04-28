package com.example.hotelbooking.services.impl;

import com.example.hotelbooking.aop.CheckExistUser;
import com.example.hotelbooking.entities.User;
import com.example.hotelbooking.entities.UserRole;
import com.example.hotelbooking.exception.EntityNotFoundException;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.repository.UserRoleRepository;
import com.example.hotelbooking.services.UserService;
import com.example.hotelbooking.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository roleRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Пользователь с именем {0} не найден", username))
        );
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Пользователь с ID {0} не найден", id))
        );
    }

    @Override
    @CheckExistUser
    public User save(User user, UserRole userRole){
        user.setRoles(Collections.singletonList(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        userRole.setUser(savedUser);
        UserRole savedRole = roleRepository.save(userRole);
        return savedUser;
    }

    @Override
    public User update(Long id, User user) {
        User existeduser = findById(id);
        Utils.copyNonNullValues(user, existeduser);
        return userRepository.save(existeduser);
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id);
    }
}
