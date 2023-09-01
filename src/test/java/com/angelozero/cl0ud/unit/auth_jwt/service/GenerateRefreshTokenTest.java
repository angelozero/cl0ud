package com.angelozero.cl0ud.unit.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.service.FindUserByEmail;
import com.angelozero.cl0ud.auth_jwt.service.GenerateRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.SaveRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenerateRefreshTokenTest {

    @InjectMocks
    private GenerateRefreshToken generateRefreshToken;

    @Mock
    private FindUserByEmail findUserByEmail;

    @Mock
    private SaveRefreshToken saveRefreshToken;


    @DisplayName("Should generate refresh token with success")
    @Test
    void testShouldGenerateRefreshTokenWithSuccess() {

        when(findUserByEmail.execute(anyString()))
                .thenReturn(User.builder().fullname("user test").build());
        when(saveRefreshToken.execute(any(TokenRefreshed.class)))
                .thenReturn(TokenRefreshed.builder().token("token test").build());

        TokenRefreshed response = generateRefreshToken.execute("email");

        assertNotNull(response);

        verify(findUserByEmail, times(1)).execute(anyString());
        verify(saveRefreshToken, times(1)).execute(any(TokenRefreshed.class));
    }
}
