package com.angelozero.cl0ud.auth_jwt.service.validation;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractUserNameByToken {

    private final ExtractClaim extractClaim;

    public String execute(String jwtToken) {
        return extractClaim.execute(jwtToken, Claims::getSubject);
    }
}
