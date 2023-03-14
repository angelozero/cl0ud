package com.angelozero.cl0ud.jwt.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckValidToken {

    private final CheckTokenExpired checkTokenExpired;
    private final ExtractUserNameByToken extractUserNameByToken;

    public boolean execute(String token, UserDetails userDetails) {
        final String userName = extractUserNameByToken.execute(token);

        return userName.equals(userDetails.getUsername()) && !checkTokenExpired.execute(token);
    }
}
