package com.springboot.jwt.resume.repository;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.resume.entity.BackendResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BackendResumeRepository extends JpaRepository<BackendResume, Long> {
    Optional<BackendResume> findById(Long id);
}