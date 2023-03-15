package com.angelozero.cl0ud.unit.jwt.service;

import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.jwt.gateway.UserRepository;
import com.angelozero.cl0ud.jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.jwt.service.GenerateToken;
import com.angelozero.cl0ud.jwt.service.UserAuthenticate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAuthenticateTest {

    @Mock
    private UserRepository repository;

    @Mock
    private GenerateToken generateToken;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserAuthenticate userAuthenticate;

    @DisplayName("Should authenticate user with success")
    @Test
    void testShouldAuthenticateUserWithSuccess() {

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(repository.findUserByEmail(anyString())).thenReturn(UserEntity.builder()
                .id(1L)
                .fullname("user-test")
                .build());
        when(generateToken.execute(any())).thenReturn("token-jwt");

        AuthenticationResponse response = userAuthenticate.execute("email-test@test.com", "pass123");

        assertNotNull(response);


    }
}
