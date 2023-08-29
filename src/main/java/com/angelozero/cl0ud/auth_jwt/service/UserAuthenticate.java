package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.UserRepository;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.service.dao.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticate {

    private final UserRepository repository;
    private final GenerateToken generateToken;
    private final AuthenticationManager authenticationManager;


    public Authentication execute(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        UserEntity userEntity = repository.findUserByEmail(email);

        String jwtToken = generateToken.execute(userEntity);

        return Authentication.builder()
                .token(jwtToken)
                .build();
    }
}
