package com.springboot.jwt.login.repository;

import com.springboot.jwt.login.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findByEmail(String email);
    Optional<Email> findByEmailAndVerificationCode(String email, String verificationCode);
    void deleteByEmail(String email);
}