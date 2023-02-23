package com.angelozero.cl0ud.usecase.jwt;

import com.angelozero.cl0ud.exception.jwt.BearerTokenJwtAuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ResolveToken {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    public String execute(HttpServletRequest request) throws BearerTokenJwtAuthException {

        String bearerToken = request.getHeader(AUTHORIZATION);

        if (!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(BEARER.length());
        }
        throw new BearerTokenJwtAuthException("Could not found the Bearer token in the request");
    }
}
