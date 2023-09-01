package com.angelozero.cl0ud.unit.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.service.DeleteToken;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTokenTest {

    @InjectMocks
    private DeleteToken deleteToken;

    @Mock
    private TokenGateway tokenGateway;

    @Mock
    private RefreshTokenMapper mapper;

    @DisplayName("Should delete token with success")
    @Test
    void shouldDeleteTokenWithSuccess() {

        when(mapper.toEntity(any(TokenRefreshed.class)))
                .thenReturn(RefreshTokenEntity.builder().build());
        doNothing().when(tokenGateway).delete(any(RefreshTokenEntity.class));

        assertDoesNotThrow(() -> deleteToken.execute(TokenRefreshed.builder().build()));

        verify(mapper, times(1)).toEntity(any(TokenRefreshed.class));
        verify(tokenGateway, times(1)).delete(any(RefreshTokenEntity.class));
    }

    @DisplayName("Should fail when delete token")
    @Test
    void shouldFailDeleteToken() {

        when(mapper.toEntity(any(TokenRefreshed.class)))
                .thenReturn(RefreshTokenEntity.builder().build());
        doThrow(new RuntimeException("fail delete token"))
                .when(tokenGateway).delete(any(RefreshTokenEntity.class));

        JwtException exception = assertThrows(JwtException.class, () ->
                deleteToken.execute(TokenRefreshed.builder().build()));

        assertNotNull(exception);
        assertEquals("[AUTH_JWT - ERROR] - DeleteToken: Error to delete token - fail delete token",
                exception.getMessage());

        verify(mapper, times(1)).toEntity(any(TokenRefreshed.class));
        verify(tokenGateway, times(1)).delete(any(RefreshTokenEntity.class));
    }
}
