package com.angelozero.cl0ud.unit.jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.exception.jwt.JwtException;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.service.GenerateToken;
import com.angelozero.cl0ud.auth_jwt.service.UserAccessByRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.dao.Authentication;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
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
    private TokenGateway tokenGateway;

    @Mock
    private GenerateToken generateToken;

    @Mock
    private RefreshTokenMapper mapper;

    @DisplayName("Should give access to an user by refresh token with time")
    @Test
    void testShouldGiveAccessToAnUserByRefreshTokenWithTimeWithSuccess() {

        when(tokenGateway.findByToken(anyString()))
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

        verify(tokenGateway, times(0)).delete(any(RefreshTokenEntity.class));
    }

    @DisplayName("Should give access to an user by refresh token without time")
    @Test
    void testShouldGiveAccessToAnUserByRefreshTokenWithoutTimeWitSuccess() {

        when(tokenGateway.findByToken(anyString()))
                .thenReturn(RefreshTokenEntity.builder().build());
        when(mapper.toModel(any(RefreshTokenEntity.class)))
                .thenReturn(TokenRefreshed.builder().expiryDate(Instant.now().minusSeconds(60)).build());
        when(mapper.toEntity(any(TokenRefreshed.class)))
                .thenReturn(RefreshTokenEntity.builder()
                        .user(UserEntity.builder().build())
                        .token(UUID.randomUUID().toString())
                        .build());

        JwtException exception = assertThrows(JwtException.class, () -> userAccessByRefreshToken.execute("token"));

        verify(tokenGateway, times(1)).delete(any(RefreshTokenEntity.class));

        assertNotNull(exception);
        assertEquals("[AUTH_JWT - ERROR] - Refresh Token is expired", exception.getMessage());

    }
}
