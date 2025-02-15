package com.springboot.jwt.resume.repository;

import com.springboot.jwt.resume.entity.FrontendResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FrontendResumeRepository extends JpaRepository<FrontendResume, Long> {
    Optional<FrontendResume> findById(Long id);
}