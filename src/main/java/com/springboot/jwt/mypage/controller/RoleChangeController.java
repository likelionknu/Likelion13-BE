package com.springboot.jwt.mypage.controller;

import com.springboot.jwt.login.config.UserRole;
import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.jwt.JwtTokenUtil;
import com.springboot.jwt.login.repository.UserRepository;
import com.springboot.jwt.mypage.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3000", "https://likelion13-fe.vercel.app"})
public class RoleChangeController {
    private final UserPageService userPageService;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil; // 변경: JwtTokenUtil을 빈으로 주입받음

    @PutMapping("/change-role")
    public ResponseEntity<?> changeUserRole(@RequestParam String email, @RequestParam UserRole role) {
        userPageService.changeUserRoleByEmail(email, role);

        User updatedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일을 찾을 수 없습니다: " + email));

        long expireTimeMs = 1000 * 60 * 60;
        String newToken = jwtTokenUtil.createToken(updatedUser.getEmail(), expireTimeMs);

        return ResponseEntity.ok(Map.of("message", "User role updated successfully", "newToken", newToken));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }


}