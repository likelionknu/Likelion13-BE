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
public class RoleChangeController {
    private final UserPageService userPageService;
    private final UserRepository userRepository;

    @PutMapping("/change-role")
    public ResponseEntity<?> changeUserRole(@RequestParam String email, @RequestParam UserRole role) {
        userPageService.changeUserRoleByEmail(email, role); // 권한 변경

        // 변경된 유저 정보 가져오기
        User updatedUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("이메일을 찾을 수 없습니다." + email));

        // 변경된 권한으로 새로운 토큰 발급
        String secretKey = "YXYtdmVyeS1sb25nLXNlY3JldC1rZXktd2hpY2gtaXM=12341234";
        long expireTimeMs = 1000 * 60 * 60;
        String newToken = JwtTokenUtil.createToken(updatedUser.getEmail(), updatedUser.getRole(), secretKey, expireTimeMs);

        return ResponseEntity.ok(Map.of("message", "User role updated successfully", "newToken", newToken));
    }

    // 유저 리스트 보기
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
