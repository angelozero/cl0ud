package com.angelozero.cl0ud.usecase.jwt;

import com.angelozero.cl0ud.exception.jwt.InvalidJwtAuthException;
import com.angelozero.cl0ud.gateway.JwtSecurityGateway;
import com.angelozero.cl0ud.usecase.jwt.mapper.UserMapper;
import com.angelozero.cl0ud.usecase.jwt.model.Token;
import com.angelozero.cl0ud.usecase.jwt.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Login {

    @Autowired
    private final AuthenticationManager authenticationManager;

    private final JwtSecurityGateway jwtSecurityGateway;

    private final CreateAccessToken createAccessToken;

    private final UserMapper userMapper;

    public Token execute(String userName, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            User user = userMapper.toModel(jwtSecurityGateway.findByUserName(userName));

            return Optional.ofNullable(user)
                    .map(data -> createAccessToken.execute(data.getUsername(), data.getRoles()))
                    .orElseThrow(() -> new InvalidJwtAuthException("LOGIN ERROR"));

        } catch (Exception ex) {
            throw new InvalidJwtAuthException("LOGIN ERROR");
        }
    }
}
