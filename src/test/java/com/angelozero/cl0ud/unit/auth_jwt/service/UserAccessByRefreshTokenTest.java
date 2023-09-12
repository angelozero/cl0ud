package com.angelozero.cl0ud.unit.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.service.FindRefreshTokenByToken;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.service.GenerateToken;
import com.angelozero.cl0ud.auth_jwt.service.UserAccessByRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenData;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
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
public class UserAccessByRefreshTokenTest {

    @InjectMocks
    private UserAccessByRefreshToken userAccessByRefreshToken;

    @Mock
    private FindRefreshTokenByToken findRefreshTokenByToken;

    @Mock
    private GenerateToken generateToken;

    @Mock
    private RefreshTokenMapper mapper;

    @DisplayName("Should give access to an user by refresh token with time")
    @Test
    void testShouldGiveAccessToAnUserByRefreshTokenWithTimeWithSuccess() {

        when(findRefreshTokenByToken.execute(anyString()))
                .thenReturn(TokenRefreshed.builder().build());
        when(mapper.toEntity(any(TokenRefreshed.class)))
                .thenReturn(RefreshTokenEntity.builder()
                        .user(UserEntity.builder().build())
                        .token(UUID.randomUUID().toString())
                        .build());
        when(generateToken.execute(any(UserEntity.class))).thenReturn(UUID.randomUUID().toString());

        TokenData response = userAccessByRefreshToken.execute("token");

        assertNotNull(response);

        verify(findRefreshTokenByToken, times(1)).execute(anyString());
        verify(mapper, times(1)).toEntity(any(TokenRefreshed.class));
        verify(generateToken, times(1)).execute(any(UserEntity.class));
    }
}
