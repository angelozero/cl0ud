package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.config.JwtPropertiesConfiguration;
import com.angelozero.cl0ud.auth_jwt.service.utils.JwtJsonUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateToken {

    private final JwtPropertiesConfiguration jwtPropsConfig;

    public String execute(UserDetails userDetails) {
        return execute(new HashMap<>(), userDetails);
    }

    public String execute(Map<String, Object> claims, UserDetails userDetails) {
        log.info("\n[GENERATE_TOKEN] - Generating token with user: {}\n", JwtJsonUtils.generateJson(userDetails));
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
