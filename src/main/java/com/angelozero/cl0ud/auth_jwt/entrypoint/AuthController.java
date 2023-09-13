package com.angelozero.cl0ud.auth_jwt.entrypoint;

import com.angelozero.cl0ud.auth_jwt.entrypoint.mapper.UserRestMapper;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.AuthenticationRequest;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.RefreshTokenRequest;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.RegisterRequest;
import com.angelozero.cl0ud.auth_jwt.service.GenerateRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.UserAccessByRefreshToken;
import com.angelozero.cl0ud.auth_jwt.service.RegisterUser;
import com.angelozero.cl0ud.auth_jwt.service.AuthenticateUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateUser authenticateUser;
    private final RegisterUser registerUser;
    private final GenerateRefreshToken generateRefreshToken;
    private final UserAccessByRefreshToken userAccessByRefreshToken;
    private final UserRestMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest body) {
        registerUser.execute(mapper.toUser(body));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest body) {
        String tokenRefreshed = generateRefreshToken.execute(body.getEmail()).getToken();
        AuthenticationResponse authResponse = mapper.toAuthenticateResponse(authenticateUser.execute(body.getEmail(), body.getPassword()));
        authResponse.setRefreshToken(tokenRefreshed);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest body) {
        return ResponseEntity.ok(mapper.toAuthenticateResponse(userAccessByRefreshToken.execute(body.getRefreshToken())));
    }
}
