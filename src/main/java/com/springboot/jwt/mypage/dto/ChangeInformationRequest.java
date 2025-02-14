package com.springboot.jwt.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeInformationRequest {
    private String department;
    private String password;
    private String passwordCheck;
}
