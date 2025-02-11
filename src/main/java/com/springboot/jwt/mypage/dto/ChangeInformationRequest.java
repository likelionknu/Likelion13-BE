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
    private String name;
    private String department;
    private String studentId;
    private String phone;
    private String password;
    private String passwordCheck;
    private String grade;
}
