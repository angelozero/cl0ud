package com.angelozero.cl0ud.unit.jwt.service.validation;

import com.angelozero.cl0ud.auth_jwt.service.validation.ExtractClaim;
import com.angelozero.cl0ud.auth_jwt.service.validation.ExtractUserNameByToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExtractUserNameByTokenTest {

    @Mock
    private ExtractClaim extractClaim;

    @InjectMocks
    private ExtractUserNameByToken extractUserNameByToken;


    @DisplayName("Should extract user name by token with success")
    @Test
    void testShouldExtractUserNameByTokenWithSuccess() {

        when(extractClaim.execute(any(), any())).thenReturn("Test");

        String response = extractUserNameByToken.execute("JWT test");

        assertNotNull(response);

    }
}
