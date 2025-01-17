package com.springboot.jwt.repository;

import com.springboot.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsBystudentId(String studentId);
    boolean existsByPhone(String phone);
    Optional<User> findByEmail(String email);

}
