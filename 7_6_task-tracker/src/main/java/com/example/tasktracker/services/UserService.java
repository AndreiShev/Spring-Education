package com.example.tasktracker.services;

import com.example.tasktracker.entity.RoleType;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.example.tasktracker.utils.Utils.copyNonNullValues;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Mono<User> createNewAccount(User user, RoleType roleType) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<RoleType> roles = new HashSet<>();
        roles.add(roleType);
        user.setRoles(roles);
        return userRepository.save(user);
    }


    public Mono<User> updateUser(User user, String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                .flatMap(currentUser -> {
                    copyNonNullValues(user, currentUser);
                    return userRepository.save(currentUser);
                });
    }

    public void delete(String id) {
        userRepository.deleteById(id).subscribe();
    }
}
