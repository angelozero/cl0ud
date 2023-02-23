package com.angelozero.cl0ud.entrypoint.jwt.rest.response;

import lombok.*;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String userName;
    private String fullName;
    private String password;
}
