package com.example.tasktracker.security;

import com.example.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.security", name = "type", havingValue = "db")
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserRepository userRepository;


    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {
        return Mono.fromCallable(() -> userRepository.findByUsername(username))
                .flatMap(Mono::just)
                .flatMap(test -> test.map(user -> new AppUserPrincipal(user)));
    }
}
