package com.angelozero.cl0ud.auth_jwt.service.dao;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {

    USER("user"),
    ADMIN("admin");

    private final String roleInfo;

    Role(String roleInfo) {
        this.roleInfo = roleInfo;
    }

    public static boolean contains(String role) {
        return Arrays.stream(Role.values())
                .anyMatch(secretUser -> secretUser.getRoleInfo().equals(role.toLowerCase()));
    }
}
