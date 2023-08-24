package com.angelozero.cl0ud.unit.jwt.service;

import com.angelozero.cl0ud.jwt.gateway.RefreshTokenRepository;
import com.angelozero.cl0ud.jwt.gateway.UserRepository;
import com.angelozero.cl0ud.jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.jwt.service.GenerateRefreshToken;
import com.angelozero.cl0ud.jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.jwt.service.dao.User;
import com.angelozero.cl0ud.jwt.service.mapper.RefreshTokenMapper;
import com.angelozero.cl0ud.jwt.service.mapper.UserMapper;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenerateRefreshTokenTest {

    @InjectMocks
    private GenerateRefreshToken generateRefreshToken;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RefreshTokenMapper refreshTokenMapper;


    @DisplayName("Should generate refresh token with success")
    @Test
    void testShouldGenerateRefreshTokenWithSuccess() {

        when(userRepository.findUserByEmail(anyString()))
                .thenReturn(UserEntity.builder().fullname("user test").build());
        when(userMapper.toModel(any(UserEntity.class))).thenReturn(User.builder().fullname("user test").build());
        when(refreshTokenMapper.toEntity(any(TokenRefreshed.class))).thenReturn(RefreshTokenEntity.builder().token(UUID.randomUUID().toString()).build());
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(RefreshTokenEntity.builder().build());
        when(refreshTokenMapper.toModel(any(RefreshTokenEntity.class))).thenReturn(TokenRefreshed.builder().token("token test").build());

        TokenRefreshed response = generateRefreshToken.execute("email");

        assertNotNull(response);
    }
}
