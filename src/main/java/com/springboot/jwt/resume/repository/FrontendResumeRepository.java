package com.springboot.jwt.resume.repository;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.result.entity.Result;
import com.springboot.jwt.resume.entity.BackendResume;
import com.springboot.jwt.resume.entity.DesignResume;
import com.springboot.jwt.resume.entity.FrontendResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FrontendResumeRepository extends JpaRepository<FrontendResume, Long> {
    //Optional<FrontendResume> findById(Long id);
    List<FrontendResume> findAllByApply(boolean apply);
    Optional<FrontendResume> findByUser(User user);
    Optional<FrontendResume> findByUser_StudentId(String studentId);

}