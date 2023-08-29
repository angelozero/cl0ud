package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.exception.jwt.JwtValidationException;
import com.angelozero.cl0ud.auth_jwt.gateway.UserRepository;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegister {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public void execute(User user) {

        if (repository.findUserByEmail(user.getEmail()) != null) {
            throw new JwtValidationException("User already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(mapper.toEntity(user));
    }
}
