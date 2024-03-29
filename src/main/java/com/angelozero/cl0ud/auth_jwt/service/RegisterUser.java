package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.mapper.UserMapper;
import com.angelozero.cl0ud.auth_jwt.exception.JwtException;
import com.angelozero.cl0ud.auth_jwt.exception.JwtUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterUser {

    private final TokenGateway tokenGateway;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public void execute(User user) {
        try {
            Boolean isUserFound = Optional.ofNullable(tokenGateway.findUserByEmail(user.getEmail()))
                    .map(userFound -> userFound.getEmail().equalsIgnoreCase(user.getEmail()))
                    .stream()
                    .findFirst()
                    .orElse(false);

            if (isUserFound) {
                throw new JwtUserNotFoundException("UserRegister: User already exists!");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            tokenGateway.save(mapper.toEntity(user));

        } catch (Exception ex) {
            if (ex instanceof JwtUserNotFoundException) {
                throw ex;

            } else {
                throw new JwtException("UserRegister: Was not possible register the user - " + ex.getMessage());
            }
        }
    }
}
