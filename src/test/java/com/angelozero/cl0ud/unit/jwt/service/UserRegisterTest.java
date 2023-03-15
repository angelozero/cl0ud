package com.angelozero.cl0ud.unit.jwt.service;

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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        AuthenticationResponse response = userRegister.execute(User.builder().build());

        assertNotNull(response);
    }
}
