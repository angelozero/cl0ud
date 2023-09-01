package com.angelozero.cl0ud.unit.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.service.FindUserByEmail;
import com.angelozero.cl0ud.auth_jwt.service.GenerateRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import com.angelozero.cl0ud.auth_jwt.service.mapper.UserMapper;
import com.angelozero.cl0ud.exception.jwt.JwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindUserByEmailTest {

    @InjectMocks
    private FindUserByEmail findUserByEmail;

    @Mock
    private TokenGateway tokenGateway;

    @Mock
    private UserMapper userMapper;


    @DisplayName("Should find user by email with success")
    @Test
    void testShouldFindUserByEmailWithSuccess() {

        when(tokenGateway.findUserByEmail(anyString()))
                .thenReturn(UserEntity.builder().fullname("user test").build());
        when(userMapper.toModel(any(UserEntity.class)))
                .thenReturn(User.builder().fullname("user test").build());

        User response = findUserByEmail.execute("email");

        assertNotNull(response);

        verify(tokenGateway, times(1)).findUserByEmail(anyString());
        verify(userMapper, times(1)).toModel(any(UserEntity.class));
    }

    @DisplayName("Should fail when find user by email")
    @Test
    void testShouldFailFindUserByEmail() {

        when(tokenGateway.findUserByEmail(anyString()))
                .thenThrow(new RuntimeException("error find user by email"));

        JwtException exception = assertThrows(JwtException.class, () -> findUserByEmail.execute("email@test.com"));

        assertNotNull(exception);
        assertEquals("[AUTH_JWT - ERROR] - FindUserByEmail: User was not found with the email - email@test.com - Error: error find user by email",
                exception.getMessage());

        verify(tokenGateway, times(1)).findUserByEmail(anyString());
        verify(userMapper, times(0)).toModel(any(UserEntity.class));
    }
}
