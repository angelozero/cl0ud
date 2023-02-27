package com.angelozero.cl0ud.jwt.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExtractExpiration {

    private final ExtractClaim extractClaim;

    public Date execute(String token) {
        return extractClaim.execute(token, Claims::getExpiration);
    }
}
