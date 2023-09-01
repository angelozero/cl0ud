package com.angelozero.cl0ud.unit.auth_jwt.service.validation;

import com.angelozero.cl0ud.auth_jwt.service.validation.CheckTokenExpired;
import com.angelozero.cl0ud.auth_jwt.service.validation.CheckValidToken;
import com.angelozero.cl0ud.auth_jwt.service.validation.ExtractUserNameByToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckValidTokenTest {

    public static final String USER_NAME_TEST = "User Name Test";
    @Mock
    private CheckTokenExpired checkTokenExpired;

    @Mock
    private ExtractUserNameByToken extractUserNameByToken;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private CheckValidToken checkValidToken;

    @DisplayName("Should test if is a valid token")
    @Test
    void testShouldCheckIfIsAValidaToken() throws ParseException {

        when(extractUserNameByToken.execute(any())).thenReturn(USER_NAME_TEST);
        when(userDetails.getUsername()).thenReturn(USER_NAME_TEST);
        when(checkTokenExpired.execute(anyString())).thenReturn(Boolean.FALSE);

        assertTrue(checkValidToken.execute("valid token", userDetails));
    }

    @DisplayName("Should test if is a invalid token with expired token")
    @Test
    void testShouldCheckIfIsAInValidaTokenWithExpiredToken() throws ParseException {

        when(extractUserNameByToken.execute(any())).thenReturn(USER_NAME_TEST);
        when(userDetails.getUsername()).thenReturn(USER_NAME_TEST);
        when(checkTokenExpired.execute(anyString())).thenReturn(Boolean.TRUE);

        assertFalse(checkValidToken.execute("valid token", userDetails));
    }

    @DisplayName("Should test if is a invalid token with different user name")
    @Test
    void testShouldCheckIfIsAInValidaTokenWithInvalidUserName() throws ParseException {

        when(extractUserNameByToken.execute(any())).thenReturn(USER_NAME_TEST);
        when(userDetails.getUsername()).thenReturn("Fake Name");

        assertFalse(checkValidToken.execute("valid token", userDetails));
    }

}
