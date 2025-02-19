package com.springboot.jwt.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
    private String newPasswordCheck;
}
