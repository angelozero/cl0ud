package com.angelozero.cl0ud.unit.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.service.SaveRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import com.angelozero.cl0ud.exception.jwt.JwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveRefreshTokenTest {

    @InjectMocks
    private SaveRefreshToken saveRefreshToken;

    @Mock
    private TokenGateway tokenGateway;

    @Mock
    private RefreshTokenMapper refreshTokenMapper;


    @DisplayName("Should save refresh token with success")
    @Test
    void testShouldSaveRefreshTokenWithSuccess() {

        when(refreshTokenMapper.toEntity(any(TokenRefreshed.class)))
                .thenReturn(RefreshTokenEntity.builder().token("user test").build());
        when(tokenGateway.save(any(RefreshTokenEntity.class)))
                .thenReturn(RefreshTokenEntity.builder().token("token-test").build());
        when(refreshTokenMapper.toModel(any(RefreshTokenEntity.class)))
                .thenReturn(TokenRefreshed.builder().token("user test").build());

        TokenRefreshed response = saveRefreshToken.execute(TokenRefreshed.builder().build());

        assertNotNull(response);

        verify(refreshTokenMapper, times(1)).toEntity(any(TokenRefreshed.class));
        verify(tokenGateway, times(1)).save(any(RefreshTokenEntity.class));
        verify(refreshTokenMapper, times(1)).toModel(any(RefreshTokenEntity.class));
    }

    @DisplayName("Should fail when save refresh token")
    @Test
    void testShouldFailWhenSaveTokenRefresh() {

        when(refreshTokenMapper.toEntity(any(TokenRefreshed.class)))
                .thenReturn(RefreshTokenEntity.builder().token("user test").build());
        when(tokenGateway.save(any(RefreshTokenEntity.class)))
                .thenThrow(new RuntimeException("error to save refresh token"));

        JwtException exception = assertThrows(JwtException.class, () -> saveRefreshToken.execute(TokenRefreshed.builder().build()));

        assertNotNull(exception);
        assertEquals("[AUTH_JWT - ERROR] - SaveRefreshToken: Error to save the refresh token - error to save refresh token",
                exception.getMessage());

        verify(refreshTokenMapper, times(1)).toEntity(any(TokenRefreshed.class));
        verify(tokenGateway, times(1)).save(any(RefreshTokenEntity.class));
        verify(refreshTokenMapper, times(0)).toModel(any(RefreshTokenEntity.class));
    }
}
