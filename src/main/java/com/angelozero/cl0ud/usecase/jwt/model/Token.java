package com.angelozero.cl0ud.usecase.jwt.model;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String userName;
    private Boolean authenticated;
    private LocalDateTime created;
    private LocalDateTime expiration;
    private String accessToken;
    private String refreshToken;
}
