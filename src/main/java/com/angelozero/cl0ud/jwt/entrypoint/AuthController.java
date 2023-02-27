package com.angelozero.cl0ud.jwt.entrypoint;

import com.angelozero.cl0ud.jwt.entrypoint.mapper.UserRequestMapper;
import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationRequest;
import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.jwt.entrypoint.rest.RegisterRequest;
import com.angelozero.cl0ud.jwt.service.RegisterService;
import com.angelozero.cl0ud.jwt.service.AuthenticateService;
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

    private final AuthenticateService authenticateService;
    private final RegisterService registerService;
    private final UserRequestMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest body) {
        return ResponseEntity.ok(registerService.execute(mapper.toUser(body)));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest body) {
        return ResponseEntity.ok(authenticateService.execute(body.getEmail(), body.getPassword()));
    }
}
