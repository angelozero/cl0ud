package com.angelozero.cl0ud.jwt.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractUserName {

    private final ExtractClaim extractClaim;

    public String execute(String jwtToken) {
        return extractClaim.execute(jwtToken, Claims::getSubject);
    }
}
