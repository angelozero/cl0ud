package com.angelozero.cl0ud.usecase.jwt.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Permission implements GrantedAuthority {

    private Long id;
    private String description;

    @Override
    public String getAuthority() {
        return this.description;
    }
}
