package com.springboot.jwt.dto;

import com.springboot.jwt.config.UserRole;
import com.springboot.jwt.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    private String name;
    private String major;
    private String schoolNum;
    private String phoneNum;
    private String email;
    private String password;
    private String passwordCheck;
    private String grade;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .name(this.name)
                .major(this.major)
                .schoolNum(this.schoolNum)
                .phoneNum(this.phoneNum)
                .email(this.email)
                .password(encodedPassword) // 암호화된 비밀번호 사용
                .grade(this.grade)
                .role(UserRole.USER)
                .build();
    }
}
