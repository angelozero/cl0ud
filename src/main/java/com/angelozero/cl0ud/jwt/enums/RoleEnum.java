package com.angelozero.cl0ud.jwt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    NO_USER("no_user"),
    USER("user"),
    ADMIN("admin");

    private final String roleInfo;

    public static boolean contains(String role) {
        return Arrays.stream(RoleEnum.values())
                .anyMatch(secretUser -> secretUser.getRoleInfo().equals(role.toLowerCase()));
    }

    public static RoleEnum getRoleInfo(String role) {
        return Arrays.stream(RoleEnum.values())
                .map(roleEnum -> RoleEnum.valueOf(role))
                .findAny().orElse(NO_USER);
    }
}
