package com.angelozero.cl0ud.auth_jwt.gateway.impl;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.repository.RefreshTokenRepository;
import com.angelozero.cl0ud.auth_jwt.gateway.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenGatewayImpl implements TokenGateway {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public RefreshTokenEntity findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokenEntity save(RefreshTokenEntity refreshTokenEntity) {
        return refreshTokenRepository.save(refreshTokenEntity);
    }

    @Override
    public void delete(RefreshTokenEntity refreshTokenEntity) {
        refreshTokenRepository.delete(refreshTokenEntity);
    }

    @Override
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
