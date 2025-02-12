package com.springboot.jwt.result.repository;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.result.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByUser(User user);
    Optional<Result> findByUserStudentId(String studentId);

}
