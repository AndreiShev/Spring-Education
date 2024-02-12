package com.example.rest.services.impl;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.model.User;
import com.example.rest.repository.UserRepository;
import com.example.rest.services.UserService;
import com.example.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll(Integer offset, Integer limit) {
        return userRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).
                orElseThrow(
                        () -> new EntityNotFoundException(MessageFormat.format("Пользователь с таким ID {0} не найден", id))
                );
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
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
}
