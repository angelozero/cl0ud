package com.angelozero.cl0ud.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsValidToken {


    private final IsTokenExpired isTokenExpired;
    private final ExtractUserName extractUserName;

    public boolean execute(String token, UserDetails userDetails) {
        final String userName = extractUserName.execute(token);

        return userName.equals(userDetails.getUsername()) && !isTokenExpired.execute(token);
    }
}
