package com.springboot.jwt.login.entity;

import com.springboot.jwt.login.config.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "user")

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "studentId", nullable = false, unique = true)
    private String studentId;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String name, String major, String schoolNum, String phoneNum, String email, String password, String grade, UserRole role, boolean emailVerified) {
        this.name = name;
        this.department = major;
        this.studentId = schoolNum;
        this.phone = phoneNum;
        this.email = email;
        this.password = password;
        this.grade = grade;
        this.role = role;
        this.emailVerified = false;
    }

    @PrePersist
    @PreUpdate
    private void addEmailDomain() {
        if (email != null && !email.contains("@")) {
            this.email = this.email + "@kangnam.ac.kr";
        }
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // true == 만료되지 않음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; // true == 잠기지 않음
    }

    // 패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // true == 만료되지 않음
    }

    // 계정 사용 여부 반환
    @Override
    public boolean isEnabled() {
        return true; // true == 사용 가능
    }

}