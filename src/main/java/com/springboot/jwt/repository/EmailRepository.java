package com.springboot.jwt.repository;

import com.springboot.jwt.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findByEmail(String email);
    Optional<Email> findByEmailAndVerificationCode(String email, String verificationCode);
    void deleteByEmail(String email);
}