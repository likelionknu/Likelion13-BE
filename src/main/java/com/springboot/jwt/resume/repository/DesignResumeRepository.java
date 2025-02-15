package com.springboot.jwt.resume.repository;

import com.springboot.jwt.resume.entity.DesignResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesignResumeRepository extends JpaRepository<DesignResume, Long> {
    Optional<DesignResume> findById(Long id);
}