package com.angelozero.cl0ud.auth_jwt.service.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshed {

    private int id;

    private String token;

    private Instant expiryDate;

    private User user;
}
