package com.angelozero.cl0ud.unit.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.service.FindRefreshTokenByToken;
import com.angelozero.cl0ud.auth_jwt.service.VerifyExpiryRefreshTokenDate;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import com.angelozero.cl0ud.auth_jwt.exception.JwtException;
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
public class FindRefreshTokenByTokenTest {

    @InjectMocks
    private FindRefreshTokenByToken findRefreshTokenByToken;

    @Mock
    private TokenGateway tokenGateway;

    @Mock
    private VerifyExpiryRefreshTokenDate verifyExpiryRefreshTokenDate;

    @Mock
    private RefreshTokenMapper mapper;

    @DisplayName("Should find refresh token by token with success")
    @Test
    void shouldFindRefreshTokenByTokenWithSuccess() {

        when(tokenGateway.findByToken(anyString())).thenReturn(RefreshTokenEntity.builder().build());
        when(mapper.toModel(any(RefreshTokenEntity.class))).thenReturn(TokenRefreshed.builder().build());
        doNothing().when(verifyExpiryRefreshTokenDate).execute(any(TokenRefreshed.class));

        TokenRefreshed response = findRefreshTokenByToken.execute("token");

        assertNotNull(response);

        verify(tokenGateway, times(1)).findByToken(anyString());
        verify(mapper, times(1)).toModel(any(RefreshTokenEntity.class));
        verify(verifyExpiryRefreshTokenDate, times(1)).execute(any(TokenRefreshed.class));
    }

    @DisplayName("Should fail find refresh token by token")
    @Test
    void shouldFailFindRefreshTokenByToken() {

        when(tokenGateway.findByToken(anyString())).thenThrow(new RuntimeException("fail find refresh token by token"));

        JwtException exception = assertThrows(JwtException.class, () -> findRefreshTokenByToken.execute("token"));

        assertNotNull(exception);
        assertEquals("[AUTH_JWT - ERROR] - TokenRefreshed: Error to find token by token - fail find refresh token by token",
                exception.getMessage());

        verify(tokenGateway, times(1)).findByToken(anyString());
        verify(mapper, times(0)).toModel(any(RefreshTokenEntity.class));
        verify(verifyExpiryRefreshTokenDate, times(0)).execute(any(TokenRefreshed.class));
    }
}
