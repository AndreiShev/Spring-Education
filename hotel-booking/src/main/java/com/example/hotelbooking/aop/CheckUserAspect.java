package com.example.hotelbooking.aop;

import com.example.hotelbooking.entities.User;
import com.example.hotelbooking.exception.EntityAlreadyExistsException;
import com.example.hotelbooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@AllArgsConstructor
public class CheckUserAspect {
    private final UserRepository repository;

    @Before(value = "execution(* com.example.hotelbooking.services.UserService.save(*)) && @annotation(CheckExistUser)")
    public void checkBeforeCreateUser(final JoinPoint joinPoint) {
        User currentUser = (User) joinPoint.getArgs()[0];

        if (repository.existsUserByUsernameAndEmail(currentUser.getUsername(), currentUser.getEmail())) {
            throw new EntityAlreadyExistsException(MessageFormat.format("Пользователь с именем {0} и почтой {1} уже существует"
                    , currentUser.getUsername(), currentUser.getEmail()));
        }

    }
}
