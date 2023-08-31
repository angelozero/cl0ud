package com.angelozero.cl0ud.unit.jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.gateway.repository.RefreshTokenRepository;
import com.angelozero.cl0ud.auth_jwt.gateway.repository.UserRepository;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.service.GenerateRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import com.angelozero.cl0ud.auth_jwt.service.mapper.UserMapper;
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
    private TokenGateway tokenGateway;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RefreshTokenMapper refreshTokenMapper;


    @DisplayName("Should generate refresh token with success")
    @Test
    void testShouldGenerateRefreshTokenWithSuccess() {

        when(tokenGateway.findUserByEmail(anyString()))
                .thenReturn(UserEntity.builder().fullname("user test").build());
        when(userMapper.toModel(any(UserEntity.class))).thenReturn(User.builder().fullname("user test").build());
        when(refreshTokenMapper.toEntity(any(TokenRefreshed.class))).thenReturn(RefreshTokenEntity.builder().token(UUID.randomUUID().toString()).build());
        when(tokenGateway.save(any(RefreshTokenEntity.class))).thenReturn(RefreshTokenEntity.builder().build());
        when(refreshTokenMapper.toModel(any(RefreshTokenEntity.class))).thenReturn(TokenRefreshed.builder().token("token test").build());

        TokenRefreshed response = generateRefreshToken.execute("email");

        assertNotNull(response);
    }
}
