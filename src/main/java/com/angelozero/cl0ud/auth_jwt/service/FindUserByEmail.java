package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.mapper.UserMapper;
import com.angelozero.cl0ud.exception.jwt.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindUserByEmail {

    private final TokenGateway tokenGateway;
    private final UserMapper userMapper;

    public User execute(String email) {

        try {
            log.info("\n[FIND_USER_BY_EMAIL] - Find user by email: {}\n", email);
            return userMapper.toModel(tokenGateway.findUserByEmail(email));

        } catch (Exception ex) {
            throw new JwtException("FindUserByEmail: User was not found with the email - " + email + " - Error: " + ex.getMessage());
        }
    }
}
