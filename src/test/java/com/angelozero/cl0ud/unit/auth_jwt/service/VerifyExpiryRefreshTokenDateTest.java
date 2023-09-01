package com.angelozero.cl0ud.unit.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.service.DeleteToken;
import com.angelozero.cl0ud.auth_jwt.service.VerifyExpiryRefreshTokenDate;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.exception.JwtDeleteTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VerifyExpiryRefreshTokenDateTest {

    @InjectMocks
    private VerifyExpiryRefreshTokenDate verifyExpiryRefreshTokenDate;

    @Mock
    private DeleteToken deleteToken;

    @DisplayName("Should validate expired refresh token with success")
    @Test
    void shouldValidateExpiredRefreshTokenWithSuccess() {

        assertDoesNotThrow(() -> verifyExpiryRefreshTokenDate.execute(TokenRefreshed.builder()
                .expiryDate(Instant.now().plusSeconds(60))
                .build()));

        verify(deleteToken, times(0)).execute(any(TokenRefreshed.class));
    }

    @DisplayName("Should fail validate expired refresh token")
    @Test
    void shouldVFailValidateExpiredRefreshToken() {

        doNothing().when(deleteToken).execute(any(TokenRefreshed.class));

        JwtDeleteTokenException exception = assertThrows(JwtDeleteTokenException.class, () ->
                verifyExpiryRefreshTokenDate.execute(TokenRefreshed.builder()
                        .expiryDate(Instant.now().minusSeconds(60))
                        .build()));

        assertNotNull(exception);
        assertEquals("[AUTH_JWT_DELETE_TOKEN - ERROR] - Refresh Token is expired", exception.getMessage());

        verify(deleteToken, times(1)).execute(any(TokenRefreshed.class));
    }
}
