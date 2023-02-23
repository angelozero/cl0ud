package com.angelozero.cl0ud.entrypoint.jwt.rest.response;

import lombok.*;

import java.util.Date;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String userName;
    private String authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
    private String refreshToken;
}
