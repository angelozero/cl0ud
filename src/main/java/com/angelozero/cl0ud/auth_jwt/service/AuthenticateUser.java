package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.exception.JwtException;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenData;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticateUser {

    private final FindUserByEmail findUserByEmail;
    private final UserMapper mapper;
    private final GenerateToken generateToken;
    private final AuthenticationManager authenticationManager;


    public TokenData execute(String email, String password) {
        try {
            log.info("\n[AUTHENTICATE_USER] - Authenticate the user: {}\n", email);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to authenticate the user: - {}\n", ex.getMessage());
            throw new JwtException("AuthenticateUser: Error to authenticate the user: - " + ex.getMessage());
        }

        User user = findUserByEmail.execute(email);

        String jwtToken = generateToken.execute(mapper.toEntity(user));

        return TokenData.builder()
                .token(jwtToken)
                .build();
    }
}
