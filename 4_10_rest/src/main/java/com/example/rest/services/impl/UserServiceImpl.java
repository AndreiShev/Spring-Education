package com.example.rest.services.impl;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.model.Role;
import com.example.rest.model.User;
import com.example.rest.repository.RoleRepository;
import com.example.rest.repository.UserRepository;
import com.example.rest.services.UserService;
import com.example.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Service

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll(Integer offset, Integer limit) {
        List<User> listUser = userRepository.findAll(PageRequest.of(offset, limit)).getContent();
        return listUser;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(
                        () -> new EntityNotFoundException(MessageFormat.format("Пользователь с таким ID {0} не найден", id))
                );

        return userRepository.findById(id).
                orElseThrow(
                        () -> new EntityNotFoundException(MessageFormat.format("Пользователь с таким ID {0} не найден", id))
                );
    }

    @Override
    public User save(User user, Role role) {
        user.setRoles(Collections.singletonList(role));
        user.setUsername(getUserName(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        role.setUser(savedUser);
        Role savedRole = roleRepository.save(role);

        return savedUser;
    }

    @Override
    public User update(User user) {
        User existedUser = findById(user.getId());
        BeanUtils.copyNonNullProperties(user, existedUser);

        return userRepository.save(existedUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    private String getUserName(User user) {
        return user.getLastName() + " " + user.getFirstName() + " (" + user.getEmail() + ")";
    }
}
