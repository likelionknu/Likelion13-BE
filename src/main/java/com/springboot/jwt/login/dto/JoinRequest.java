package com.springboot.jwt.login.dto;

import com.springboot.jwt.login.config.UserRole;
import com.springboot.jwt.login.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    private String name;
    private String department;
    private String studentId;
    private String phone;
    private String email;
    private String password;
    private String passwordCheck;
    private String grade;

    public User toEntity(String encodedPassword) {
        if (this.email != null && !this.email.contains("@")) {
            this.email = this.email + "@kangnam.ac.kr";
        }
        return User.builder()
                .name(this.name)
                .major(this.department)
                .schoolNum(this.studentId)
                .phoneNum(this.phone)
                .email(this.email)
                .password(encodedPassword) // 암호화된 비밀번호 사용
                .grade(this.grade)
                .role(UserRole.USER)
                .build();
    }
}
