package com.springboot.jwt.resume.repository;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.result.entity.Result;
import com.springboot.jwt.resume.dto.BackendResumeRequestDto;
import com.springboot.jwt.resume.entity.BackendResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BackendResumeRepository extends JpaRepository<BackendResume, Long> {
    Optional<BackendResume> findById(Long id);
    List<BackendResume> findAllByApply(boolean apply);
    Optional<BackendResume> findByUser(User user);
}