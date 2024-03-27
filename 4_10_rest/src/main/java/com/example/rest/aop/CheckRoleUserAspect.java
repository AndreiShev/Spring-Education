package com.example.rest.aop;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.exception.ObjectCantChangeException;
import com.example.rest.model.Comment;
import com.example.rest.model.Role;
import com.example.rest.model.RoleType;
import com.example.rest.model.User;
import com.example.rest.repository.UserRepository;
import com.example.rest.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class CheckRoleUserAspect {

    private final UserRepository userRepository;

    @Before(value = "execution(* com.example.rest.services.UserService.findById(*)) " +
            "|| execution(* com.example.rest.services.UserService.deleteById(*)) && @annotation(CheckRoleUser)")
    public void checkBeforeFindByIdAndDelete(final JoinPoint joinPoint) {
        Long id = (Long) joinPoint.getArgs()[0];
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = getRoles(userDetails);
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Пользователь с таким ID {0} не найден", id))
        );

        if (roles.size() == 1 && roles.get(0).equals(RoleType.ROLE_USER)
                && !user.getUsername().equals(userDetails.getUsername())) {
            throw new ObjectCantChangeException("Поиск, удаление других пользователей недоступны");
        }
    }

    @Before(value = "execution(* com.example.rest.services.UserService.update(*)) && @annotation(CheckRoleUser)")
    public void checkBeforeUpdate(final JoinPoint joinPoint) {
        User currentUser = (User) joinPoint.getArgs()[0];
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = getRoles(userDetails);
        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Пользователь с таким ID {0} не найден"
                        , currentUser.getId()))
        );

        if (roles.size() == 1 && roles.get(0).equals(RoleType.ROLE_USER)
                && !user.getUsername().equals(userDetails.getUsername())) {
            throw new ObjectCantChangeException("Обновление других пользователей недоступны");
        }
    }


    private List<String> getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream().map(grantedAuthority ->
                grantedAuthority.getAuthority()).collect(Collectors.toList());
    }
}
