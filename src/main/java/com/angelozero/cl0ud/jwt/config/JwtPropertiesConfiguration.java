package com.angelozero.cl0ud.jwt.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Data
@Configuration
public class JwtPropertiesConfiguration {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.milliseconds}")
    private Integer milliseconds;

    @Value("${security.jwt.token.hours}")
    private Integer hours;

    public Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));
    }
}
