package com.angelozero.cl0ud.unit.jwt.service;

import com.angelozero.cl0ud.exception.jwt.JwtValidationException;
import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.jwt.gateway.UserRepository;
import com.angelozero.cl0ud.jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.jwt.service.GenerateToken;
import com.angelozero.cl0ud.jwt.service.UserRegister;
import com.angelozero.cl0ud.jwt.service.dao.User;
import com.angelozero.cl0ud.jwt.service.mapper.UserMapper;
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
    private UserRepository repository;

    @Mock
    private GenerateToken generateToken;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserRegister userRegister;

    @DisplayName("Should register user with success")
    @Test
    void testShouldRegisterUserWithSuccess() {

        when(passwordEncoder.encode(any())).thenReturn("pass-123");
        when(repository.save(any())).thenReturn(UserEntity.builder()
                .id(1L)
                .fullname("user-name-test")
                .build());
        when(generateToken.execute(any())).thenReturn("jwt-token");
        when(mapper.toEntity(any())).thenReturn(UserEntity.builder().build());

        AuthenticationResponse response = userRegister.execute(User.builder().build());

        assertNotNull(response);
    }

    @DisplayName("Should failed the register if user already exists")
    @Test
    void testShouldFailRegisterUserAlreadyExist() {

        when(repository.findUserByEmail(any())).thenReturn(
                UserEntity.builder().fullname("User Test").build());

        JwtValidationException exception = assertThrows(JwtValidationException.class,
                () -> userRegister.execute(User.builder().build()));

        verify(passwordEncoder, times(0)).encode(any());
        verify(repository, times(0)).save(any());
        verify(generateToken, times(0)).execute(any());
        verify(mapper, times(0)).toEntity(any());

        assertNotNull(exception);
        assertEquals("[Jwt Validation] - User already exists!", exception.getMessage());
    }
}
