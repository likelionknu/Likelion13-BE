package com.springboot.jwt.repository;

import com.springboot.jwt.entity.User;
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
}
