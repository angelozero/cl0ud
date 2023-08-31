package com.angelozero.cl0ud.unit.jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.exception.jwt.JwtException;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.service.GenerateToken;
import com.angelozero.cl0ud.auth_jwt.service.UserRegister;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.mapper.UserMapper;
import com.angelozero.cl0ud.exception.jwt.JwtUserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRegisterTest {

    @Mock
    private TokenGateway tokenGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserRegister userRegister;

    @DisplayName("Should register user with success")
    @Test
    void testShouldRegisterUserWithSuccess() {

        User user = User.builder()
                .email("email-test-2")
                .password("password-test")
                .build();

        when(tokenGateway.findUserByEmail(any())).thenReturn(UserEntity.builder().email("email-test-1").build());
        when(passwordEncoder.encode(any())).thenReturn("pass-123");
        when(mapper.toEntity(any(User.class))).thenReturn(UserEntity.builder().build());
        doNothing().when(tokenGateway).save(any(UserEntity.class));

        assertDoesNotThrow(() -> userRegister.execute(user));

        verify(tokenGateway, times(1)).findUserByEmail(anyString());
        verify(tokenGateway, times(1)).save(any(UserEntity.class));
        verify(passwordEncoder, times(1)).encode(any());
        verify(mapper, times(1)).toEntity(any());
    }

    @DisplayName("Should failed the register if user already exists")
    @Test
    void testShouldFailRegisterUserAlreadyExist() {

        User user = User.builder()
                .email("email-test-1")
                .password("password-test")
                .build();

        when(tokenGateway.findUserByEmail(any())).thenReturn(UserEntity.builder().email("email-test-1").build());

        JwtUserNotFoundException exception = assertThrows(JwtUserNotFoundException.class,
                () -> userRegister.execute(user));

        verify(passwordEncoder, times(0)).encode(any());
        verify(tokenGateway, times(0)).save(any(UserEntity.class));
        verify(mapper, times(0)).toEntity(any());

        assertNotNull(exception);
        assertEquals("[AUTH_JWT_USER_NOT_FOUND - ERROR] - UserRegister: User already exists!", exception.getMessage());
    }

    @DisplayName("Should failed the register if there is an error in the password encoder call")
    @Test
    void testShouldFailRegisterIfThereIsAnErrorInThePasswordEncoderCall() {

        User user = User.builder()
                .email("email-test-2")
                .password("password-test")
                .build();

        when(tokenGateway.findUserByEmail(any())).thenReturn(UserEntity.builder().email("email-test-1").build());
        when(passwordEncoder.encode(any())).thenThrow(new RuntimeException("fail password encoder"));

        JwtException exception = assertThrows(JwtException.class,
                () -> userRegister.execute(user));

        verify(tokenGateway, times(1)).findUserByEmail(anyString());
        verify(passwordEncoder, times(1)).encode(any());
        verify(tokenGateway, times(0)).save(any(UserEntity.class));
        verify(mapper, times(0)).toEntity(any());

        assertNotNull(exception);
        assertEquals("[AUTH_JWT - ERROR] - UserRegister: Was not possible register the user - fail password encoder", exception.getMessage());
    }

    @DisplayName("Should failed the register if there is an error in the tokenGateway call")
    @Test
    void testShouldFailRegisterIfThereIsAnErrorInTheTokenGatewayCall() {

        User user = User.builder()
                .email("email-test-2")
                .password("password-test")
                .build();

        when(tokenGateway.findUserByEmail(any())).thenReturn(UserEntity.builder().email("email-test-1").build());
        when(passwordEncoder.encode(any())).thenReturn("pass-123");
        when(mapper.toEntity(any(User.class))).thenReturn(UserEntity.builder().build());
        doThrow(new RuntimeException("fail save user")).when(tokenGateway).save(any(UserEntity.class));

        JwtException exception = assertThrows(JwtException.class,
                () -> userRegister.execute(user));

        verify(tokenGateway, times(1)).findUserByEmail(anyString());
        verify(passwordEncoder, times(1)).encode(any());
        verify(tokenGateway, times(1)).save(any(UserEntity.class));
        verify(mapper, times(1)).toEntity(any());

        assertNotNull(exception);
        assertEquals("[AUTH_JWT - ERROR] - UserRegister: Was not possible register the user - fail save user", exception.getMessage());
    }

    @DisplayName("Should failed the register if there is an error in the tokenGateway call - find user by email")
    @Test
    void testShouldFailRegisterIfThereIsAnErrorInTheTokenGatewayCallFindUserByEmail() {

        User user = User.builder()
                .email("email-test-2")
                .password("password-test")
                .build();

        when(tokenGateway.findUserByEmail(any())).thenThrow(new RuntimeException("fail find user by email"));

        JwtException exception = assertThrows(JwtException.class,
                () -> userRegister.execute(user));

        verify(tokenGateway, times(1)).findUserByEmail(anyString());
        verify(passwordEncoder, times(0)).encode(any());
        verify(tokenGateway, times(0)).save(any(UserEntity.class));
        verify(mapper, times(0)).toEntity(any());

        assertNotNull(exception);
        assertEquals("[AUTH_JWT - ERROR] - UserRegister: Was not possible register the user - fail find user by email", exception.getMessage());
    }
}
