package com.angelozero.cl0ud.jwt.entrypoint.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterRequest {

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "fullname is required")
    private String fullname;

    @NotBlank(message = "password is required")
    private String password;

    private Integer id;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enable;
    private String role;
}
