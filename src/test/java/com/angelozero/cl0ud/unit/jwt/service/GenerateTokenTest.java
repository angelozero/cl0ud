package com.angelozero.cl0ud.unit.jwt.service;

import com.angelozero.cl0ud.jwt.config.JwtPropertiesConfiguration;
import com.angelozero.cl0ud.jwt.service.GenerateToken;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenerateTokenTest {

    @Mock
    private JwtPropertiesConfiguration jwtPropsConfig;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private GenerateToken generateToken;

    @DisplayName("Should generate token with success")
    @Test
    void testShouldGenerateTokenWithSuccess() {

        when(userDetails.getUsername()).thenReturn("Test Name");
        when(jwtPropsConfig.getHours()).thenReturn(10);
        when(jwtPropsConfig.getSignKey()).thenReturn(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode("4D6251655468566D597133743677397A24432646294A404E635266556A586E5A")));

        String response = generateToken.execute(userDetails);

        assertNotNull(response);

    }
}
