package com.angelozero.cl0ud.jwt.service;

import com.angelozero.cl0ud.jwt.config.JwtPropertiesConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class ExtractClaim {

    private final JwtPropertiesConfig jwtPropsConfig;

    public <T> T execute(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtPropsConfig.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
