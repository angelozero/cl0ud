package com.angelozero.cl0ud.unit.jwt.entrypoint;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.auth_jwt.entrypoint.AuthController;
import com.angelozero.cl0ud.auth_jwt.entrypoint.mapper.UserRestMapper;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.AuthenticationRequest;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.RefreshTokenRequest;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.RegisterRequest;
import com.angelozero.cl0ud.auth_jwt.service.GenerateRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.UserAccessByRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.UserAuthenticate;
import com.angelozero.cl0ud.auth_jwt.service.UserRegister;
import com.angelozero.cl0ud.auth_jwt.service.dao.Authentication;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.ztemplate.jwt.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserAuthenticate userAuthenticate;

    @Mock
    private UserRegister userRegister;

    @Mock
    private GenerateRefreshToken generateRefreshToken;

    @Mock
    private UserAccessByRefreshToken userAccessByRefreshToken;

    @Mock
    private UserRestMapper mapper;

    @InjectMocks
    private AuthController controller;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate");
    }

    @DisplayName("Should register with success")
    @Test
    void shouldRegisterWithSuccess() {
        RegisterRequest registerRequestFixture = Fixture.from(RegisterRequest.class).gimme(RegisterRequestTemplate.VALID_REGISTER_REQUEST);
        AuthenticationResponse authenticationResponseFixture = Fixture.from(AuthenticationResponse.class).gimme(AuthenticationResponseTemplate.VALID_AUTHENTICATION_RESPONSE);
        User userFixture = Fixture.from(User.class).gimme(UserTemplate.VALID_USER);

        when(mapper.toUser(any(RegisterRequest.class))).thenReturn(userFixture);
        doNothing().when(userRegister).execute(any(User.class));

        assertDoesNotThrow(() -> controller.register(registerRequestFixture));

    }

    @DisplayName("Should authenticate with success")
    @Test
    void shouldAuthenticateWithSuccess() {
        AuthenticationRequest authenticationRequestFixture = Fixture.from(AuthenticationRequest.class).gimme(AuthenticationRequestTemplate.VALID_AUTHENTICATION_REQUEST);
        Authentication authenticationFixture = Fixture.from(Authentication.class).gimme(AuthenticationTemplate.VALID_AUTHENTICATION);

        when(generateRefreshToken.execute(anyString())).thenReturn(TokenRefreshed.builder().token("refresh-token-test").build());
        when(userAuthenticate.execute(anyString(), anyString())).thenReturn(authenticationFixture);
        when(mapper.toAuthenticateResponse(any())).thenReturn(AuthenticationResponse.builder().token("token-test").build());

        ResponseEntity<AuthenticationResponse> response = controller.authenticate(authenticationRequestFixture);

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getBody()));
        assertNotNull(response.getBody().getToken());
        assertNotNull(response.getBody().getRefreshToken());
    }

    @DisplayName("Should get refresh token with success")
    @Test
    void shouldRefreshTokenWithSuccess() {
        RefreshTokenRequest refreshTokenRequestFixture = Fixture.from(RefreshTokenRequest.class).gimme(RefreshTokenRequestTemplate.VALID_REFRESH_TOKEN_REQUEST);
        Authentication authenticationFixture = Fixture.from(Authentication.class).gimme(AuthenticationTemplate.VALID_AUTHENTICATION);


        when(userAccessByRefreshToken.execute(anyString())).thenReturn(authenticationFixture);
        when(mapper.toAuthenticateResponse(any())).thenReturn(AuthenticationResponse.builder().token("token-test").build());

        ResponseEntity<AuthenticationResponse> response = controller.refreshToken(refreshTokenRequestFixture);

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getBody()));
        assertNotNull(response.getBody().getToken());
        assertNull(response.getBody().getRefreshToken());
    }
}
