package com.springboot.jwt.resume.repository;

import com.springboot.jwt.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findById(Long id);
    List<Resume> findAllByApply(boolean apply);
}