package com.angelozero.cl0ud.entrypoint.jwt;

import com.angelozero.cl0ud.entrypoint.jwt.mapper.UserRestMapper;
import com.angelozero.cl0ud.entrypoint.jwt.rest.response.UserResponse;
import com.angelozero.cl0ud.usecase.jwt.FindUserByUserName;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserRestMapper userRestMapper;
    private final FindUserByUserName findUserByUserName;

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getPersons() {
        return new ResponseEntity<>(userRestMapper.toResponse(findUserByUserName.execute("Angelo")), HttpStatus.OK);
    }

}
