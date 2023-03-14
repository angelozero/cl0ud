package com.angelozero.cl0ud.unit.jwt.service.validation;

import com.angelozero.cl0ud.jwt.config.JwtPropertiesConfiguration;
import com.angelozero.cl0ud.jwt.service.GenerateToken;
import com.angelozero.cl0ud.jwt.service.validation.ExtractClaim;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExtractClaimTest {


    @Mock
    private JwtPropertiesConfiguration jwtPropsConfig;

    @Mock
    private Function<Claims, String> claimsResolver;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private ExtractClaim extractClaim;

    @InjectMocks
    private GenerateToken generateToken;

    @DisplayName("Should extract claims with success")
    @Test
    void testShouldExtractClaimsWithSuccess() {

        when(jwtPropsConfig.getSignKey()).thenReturn(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode("4D6251655468566D597133743677397A24432646294A404E635266556A586E5A")));
        when(userDetails.getUsername()).thenReturn("Test Name");
        when(jwtPropsConfig.getHours()).thenReturn(100000);

        String token = generateToken.execute(userDetails);
        assertDoesNotThrow(() -> extractClaim.execute(token, claimsResolver));
    }
}
