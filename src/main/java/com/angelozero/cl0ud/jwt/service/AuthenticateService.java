package com.angelozero.cl0ud.jwt.service;

import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.jwt.gateway.UserRepository;
import com.angelozero.cl0ud.jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.jwt.service.dao.User;
import com.angelozero.cl0ud.jwt.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public AuthenticationResponse register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity userEntity = repository.save(mapper.toEntity(user));

        return getAuthenticationResponse(userEntity);
    }

    public AuthenticationResponse authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        UserEntity userEntity = repository.findUserByEmail(email);

        return getAuthenticationResponse(userEntity);
    }

    private AuthenticationResponse getAuthenticationResponse(UserEntity userEntity) {
        String jwtToken = jwtService.generateToken(userEntity);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
