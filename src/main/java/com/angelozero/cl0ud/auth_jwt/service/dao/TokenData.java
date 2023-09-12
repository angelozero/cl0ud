package com.angelozero.cl0ud.auth_jwt.service.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenData {

    private String token;
    private String refreshToken;
}
