package com.angelozero.cl0ud.jwt.service;

import com.angelozero.cl0ud.jwt.config.JwtPropertiesConfiguration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenerateToken {

    private final JwtPropertiesConfiguration jwtPropsConfig;

    public String execute(UserDetails userDetails) {
        return execute(new HashMap<>(), userDetails);
    }

    public String execute(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtPropsConfig.getHours()))
                .signWith(jwtPropsConfig.getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
