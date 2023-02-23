package com.angelozero.cl0ud.usecase.jwt;

import com.angelozero.cl0ud.config.JwtPropertiesConfig;
import com.angelozero.cl0ud.usecase.jwt.model.Token;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GetAuthentication {

    private final DecodedToken decodedToken;
    private final CreateAccessToken createAccessToken;

    @Autowired
    private final UserDetailsService userDetailsService;

    public Authentication execute(String userName, List<String> roles) {
        Token token = createAccessToken.execute(userName, roles);
        DecodedJWT decodedJWT = decodedToken.execute(token.getAccessToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, userDetails.getAuthorities());
    }

    public Authentication execute(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(token);

        return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, userDetails.getAuthorities());
    }
}
