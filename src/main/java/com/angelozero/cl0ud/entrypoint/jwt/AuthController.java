package com.angelozero.cl0ud.entrypoint.jwt;

import com.angelozero.cl0ud.entrypoint.jwt.mapper.UserRestMapper;
import com.angelozero.cl0ud.entrypoint.jwt.rest.response.TokenResponse;
import com.angelozero.cl0ud.usecase.jwt.Login;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRestMapper userRestMapper;
    private final Login login;

    @GetMapping(value = "/sigin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> sigin() {
        return new ResponseEntity<>(userRestMapper.toResponse(login.execute("angelo",
                "19bbf735b27066f2f145e602624e1b24a3fbc54cd5dfd3143fc5feea6bdee9e139ca7332d4806b9f")), HttpStatus.OK);
    }
}
