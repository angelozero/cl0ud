package com.angelozero.cl0ud.unit.jwt.service;

import com.angelozero.cl0ud.exception.jwt.JwtValidationException;
import com.angelozero.cl0ud.jwt.gateway.RefreshTokenRepository;
import com.angelozero.cl0ud.jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.jwt.service.GenerateToken;
import com.angelozero.cl0ud.jwt.service.UserAccessByRefreshToken;
import com.angelozero.cl0ud.jwt.service.dao.Authentication;
import com.angelozero.cl0ud.jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.jwt.service.dao.User;
import com.angelozero.cl0ud.jwt.service.mapper.RefreshTokenMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserAccessByRefreshTokenTest {

    @InjectMocks
    private UserAccessByRefreshToken userAccessByRefreshToken;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private GenerateToken generateToken;

    @Mock
    private RefreshTokenMapper mapper;

    @DisplayName("Should give access to an user by refresh token with time")
    @Test
    void testShouldGiveAccessToAnUserByRefreshTokenWithTimeWithSuccess() {

        when(refreshTokenRepository.findByToken(anyString()))
                .thenReturn(RefreshTokenEntity.builder().build());
        when(mapper.toModel(any(RefreshTokenEntity.class))).thenReturn(TokenRefreshed.builder().expiryDate(Instant.now().plusSeconds(60)).build());
        when(mapper.toEntity(any(TokenRefreshed.class)))
                .thenReturn(RefreshTokenEntity.builder()
                        .user(UserEntity.builder().build())
                        .token(UUID.randomUUID().toString())
                        .build());
        when(generateToken.execute(any(UserEntity.class))).thenReturn(UUID.randomUUID().toString());


        Authentication response = userAccessByRefreshToken.execute("token");

        assertNotNull(response);

        verify(refreshTokenRepository, times(0)).delete(any(RefreshTokenEntity.class));
    }

    @DisplayName("Should give access to an user by refresh token without time")
    @Test
    void testShouldGiveAccessToAnUserByRefreshTokenWithoutTimeWitSuccess() {

        when(refreshTokenRepository.findByToken(anyString()))
                .thenReturn(RefreshTokenEntity.builder().build());
        when(mapper.toModel(any(RefreshTokenEntity.class)))
                .thenReturn(TokenRefreshed.builder().expiryDate(Instant.now().minusSeconds(60)).build());
        when(mapper.toEntity(any(TokenRefreshed.class)))
                .thenReturn(RefreshTokenEntity.builder()
                        .user(UserEntity.builder().build())
                        .token(UUID.randomUUID().toString())
                        .build());

        JwtValidationException exception = assertThrows(JwtValidationException.class, () -> userAccessByRefreshToken.execute("token"));

        verify(refreshTokenRepository, times(1)).delete(any(RefreshTokenEntity.class));

        assertNotNull(exception);
        assertEquals("[Jwt Validation] - Refresh Token is expired", exception.getMessage());

    }
}
