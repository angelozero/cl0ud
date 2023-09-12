package com.angelozero.cl0ud.unit.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.exception.JwtException;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.service.AuthenticateUser;
import com.angelozero.cl0ud.auth_jwt.service.FindUserByEmail;
import com.angelozero.cl0ud.auth_jwt.service.GenerateToken;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenData;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticateUserTest {

    @Mock
    private FindUserByEmail findUserByEmail;

    @Mock
    private UserMapper mapper;

    @Mock
    private GenerateToken generateToken;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticateUser authenticateUser;

    @DisplayName("Should authenticate user with success")
    @Test
    void testShouldAuthenticateUserWithSuccess() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(findUserByEmail.execute(anyString())).thenReturn(User.builder().build());
        when(mapper.toEntity(any(User.class))).thenReturn(UserEntity.builder().build());
        when(generateToken.execute(any(UserDetails.class))).thenReturn("jwt-token-test");

        TokenData response = authenticateUser.execute("email-test@test.com", "pass123");

        assertNotNull(response);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(findUserByEmail, times(1)).execute(anyString());
        verify(mapper, times(1)).toEntity(any(User.class));
        verify(generateToken, times(1)).execute(any(UserDetails.class));
    }

    @DisplayName("Should fail the authenticate user")
    @Test
    void testShouldFailAuthenticateUser() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("error auth test"));

        JwtException exception = assertThrows(JwtException.class, () -> authenticateUser.execute("email-test@test.com", "pass123"));

        assertNotNull(exception);

        assertEquals("[AUTH_JWT - ERROR] - AuthenticateUser: Error to authenticate the user: - error auth test", exception.getMessage());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(findUserByEmail, times(0)).execute(anyString());
        verify(mapper, times(0)).toEntity(any(User.class));
        verify(generateToken, times(0)).execute(any(UserDetails.class));
    }
}
