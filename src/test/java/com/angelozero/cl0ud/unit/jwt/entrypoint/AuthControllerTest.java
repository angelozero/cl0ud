package com.angelozero.cl0ud.unit.jwt.entrypoint;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.jwt.entrypoint.AuthController;
import com.angelozero.cl0ud.jwt.entrypoint.mapper.UserRequestMapper;
import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationRequest;
import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.jwt.entrypoint.rest.RegisterRequest;
import com.angelozero.cl0ud.jwt.service.UserAuthenticate;
import com.angelozero.cl0ud.jwt.service.UserRegister;
import com.angelozero.cl0ud.jwt.service.dao.User;
import com.angelozero.cl0ud.usecase.model.Person;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserAuthenticate userAuthenticate;

    @Mock
    private UserRegister userRegister;

    @Mock
    private UserRequestMapper mapper;

    @InjectMocks
    private AuthController controller;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate");
    }

    @DisplayName("Should register with success")
    @Test
    void shouldRegisterWithSuccess() {
        RegisterRequest registerRequestFixture = Fixture.from(RegisterRequest.class).gimme("valid RegisterRequest");
        AuthenticationResponse authenticationResponseFixture = Fixture.from(AuthenticationResponse.class).gimme("valid AuthenticationResponse");
        User userFixture = Fixture.from(User.class).gimme("valid User");

        when(mapper.toUser(any(RegisterRequest.class))).thenReturn(userFixture);
        when(userRegister.execute(any(User.class))).thenReturn(authenticationResponseFixture);

        ResponseEntity<AuthenticationResponse> response = controller.register(registerRequestFixture);

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getBody()));
        assertNotNull(response.getBody().getToken());
    }

    @DisplayName("Should authenticate with success")
    @Test
    void shouldAuthenticateWithSuccess() {
        AuthenticationRequest authenticationRequestFixture = Fixture.from(AuthenticationRequest.class).gimme("valid AuthenticationRequest");
        AuthenticationResponse authenticationResponseFixture = Fixture.from(AuthenticationResponse.class).gimme("valid AuthenticationResponse");

        when(userAuthenticate.execute(anyString(), anyString())).thenReturn(authenticationResponseFixture);

        ResponseEntity<AuthenticationResponse> response = controller.authenticate(authenticationRequestFixture);

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getBody()));
        assertNotNull(response.getBody().getToken());
    }
}
