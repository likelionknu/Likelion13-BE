package com.springboot.jwt.resume.repository;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.result.entity.Result;
import com.springboot.jwt.resume.entity.BackendResume;
import com.springboot.jwt.resume.entity.DesignResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DesignResumeRepository extends JpaRepository<DesignResume, Long> {
    //Optional<DesignResume> findByStudentId(String studentId);
    List<DesignResume> findAllByApply(boolean apply);
    Optional<DesignResume> findByUser(User user);
    Optional<DesignResume> findByUser_StudentId(String studentId);
}