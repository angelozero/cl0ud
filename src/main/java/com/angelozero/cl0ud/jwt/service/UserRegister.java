package com.angelozero.cl0ud.jwt.service;

import com.angelozero.cl0ud.exception.jwt.JwtValidationException;
import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.jwt.gateway.UserRepository;
import com.angelozero.cl0ud.jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.jwt.service.dao.User;
import com.angelozero.cl0ud.jwt.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegister {

    private final UserRepository repository;
    private final GenerateToken generateToken;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public AuthenticationResponse execute(User user) {

        if (repository.findUserByEmail(user.getEmail()) != null) {
            throw new JwtValidationException("User already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity userEntity = repository.save(mapper.toEntity(user));

        String jwtToken = generateToken.execute(userEntity);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
