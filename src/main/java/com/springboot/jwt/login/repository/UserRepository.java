package com.springboot.jwt.login.repository;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.result.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByStudentId(String studentId);
    boolean existsByPhone(String phone);
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
    Optional<User> findByStudentId(String studentId);
}
