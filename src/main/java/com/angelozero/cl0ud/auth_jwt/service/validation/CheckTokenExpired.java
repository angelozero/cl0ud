package com.angelozero.cl0ud.auth_jwt.service.validation;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CheckTokenExpired {

    private final ExtractClaim extractClaim;

    public boolean execute(String token) {
        return extractClaim.execute(token, Claims::getExpiration).before(new Date());
    }
}
