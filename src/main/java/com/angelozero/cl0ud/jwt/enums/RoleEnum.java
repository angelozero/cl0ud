package com.angelozero.cl0ud.jwt.enums;

import com.angelozero.cl0ud.exception.jwt.JwtValidationException;
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
                .anyMatch(secretUser -> secretUser.getRoleInfo().equalsIgnoreCase(role));
    }

    public static RoleEnum getRoleValue(String role) {
        if (contains(role)) {
            return RoleEnum.valueOf(role.toUpperCase());
        }
        throw new JwtValidationException("No role value was found with: " + role);
    }

    public static String getRoleInfo(String role) {
        if (contains(role)) {
            return RoleEnum.valueOf(role.toUpperCase()).getRoleInfo();
        }
        throw new JwtValidationException("No role info was found with: " + role);
    }
}
