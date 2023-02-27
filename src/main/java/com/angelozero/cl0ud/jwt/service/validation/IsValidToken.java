package com.angelozero.cl0ud.jwt.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsValidToken {

    private final IsTokenExpired isTokenExpired;
    private final ExtractUserNameByToken extractUserNameByToken;

    public boolean execute(String token, UserDetails userDetails) {
        final String userName = extractUserNameByToken.execute(token);

        return userName.equals(userDetails.getUsername()) && !isTokenExpired.execute(token);
    }
}
