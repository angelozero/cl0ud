package com.angelozero.cl0ud.entrypoint.jwt.rest.response;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String userName;
    private Boolean authenticated;
    private LocalDateTime created;
    private LocalDateTime expiration;
    private String accessToken;
    private String refreshToken;
}
