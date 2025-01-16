package com.springboot.jwt.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id", unique = true, nullable = false)
    private Long id;

    // 이메일 주소
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    // 이메일 인증 여부
    @Column(name = "isVerified", nullable = false)
    private boolean isVerified;

    @Builder
    public Email(String email, boolean isVerified, String verificationCode, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.email = email;
        this.isVerified = isVerified;
        this.verificationCode = verificationCode;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
