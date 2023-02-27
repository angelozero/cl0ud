package com.angelozero.cl0ud.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class IsTokenExpired {

    private final ExtractExpiration extractExpiration;

    public boolean execute(String token) {
        return extractExpiration.execute(token).before(new Date());
    }
}
