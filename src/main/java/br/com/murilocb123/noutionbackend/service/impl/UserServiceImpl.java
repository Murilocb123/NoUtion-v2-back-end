package br.com.murilocb123.noutionbackend.service.impl;

import br.com.murilocb123.noutionbackend.entities.UserEntity;
import br.com.murilocb123.noutionbackend.repositories.UserRepository;
import br.com.murilocb123.noutionbackend.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl  implements UserService {
    UserRepository userRepository;


    @Override
    public UserEntity get() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.equals("anonymousUser") && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return null;
        }
        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userEmail = user instanceof UserEntity ? ((UserEntity) user).getEmail() : username;
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("UserEntity not found"));
    }

    @Override
    public UUID getId() {
        var user = get();
        return user == null ? null : user.getId();
    }
}
