package com.springboot.jwt.mypage.controller;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.jwt.JwtTokenUtil;
import com.springboot.jwt.login.repository.UserRepository;
import com.springboot.jwt.mypage.dto.ChangeInformationRequest;
import com.springboot.jwt.mypage.dto.ResultDto;
import com.springboot.jwt.mypage.service.UserPageService;
import com.springboot.jwt.result.entity.Result;
import com.springboot.jwt.result.entity.ResultStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:3000", "https://likelion13-fe.vercel.app"})
public class UserPageController {
    @Autowired
    private UserPageService userPageService;
    @Autowired
    private UserRepository userRepository;

    // 유저 마이페이지 조회
    @PostMapping("/mypage-view")
    public ResponseEntity<?> getUserPage() {
        try {
            // 인증된 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (!authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
                log.warn("인증되지 않은 사용자 접근 시도");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("인증되지 않은 사용자입니다.");
            }

            // SecurityContext에서 이메일 가져오기
            String email = authentication.getName();
            log.info("요청한 사용자 이메일: {}", email);

            // 이메일로 사용자 정보 조회
            Optional<User> user = userPageService.getLoginUserByLoginId(email);

            if (user.isEmpty()) {
                log.warn("사용자 정보를 찾을 수 없음: {}", email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("사용자 정보를 찾을 수 없습니다.");
            }

            return ResponseEntity.ok(user.get()); // User 객체 반환

        } catch (Exception e) {
            log.error("유저 마이페이지 조회 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류가 발생했습니다.");
        }
    }

    // 유저 마이페이지 정보 수정
    @PutMapping("/mypage-update")
    public ResponseEntity<Object> changeInformation(@RequestBody ChangeInformationRequest changeInformationRequest) {
        // 인증된 사용자의 이메일 가져오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> optionalUser = userPageService.getLoginUserByLoginId(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            try {
                // 유저 정보 수정
                User updatedUser = userPageService.changeInformation(user, changeInformationRequest);
                return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 유저 정보를 찾을 수 없을 때
        }
    }

    // 유저 마이페이지 내 합불 조회 (결과보기)
    @PostMapping("/check-pass-fail")
    public ResponseEntity<?> getResult() {
        try {
            // 인증된 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (!authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
                log.warn("인증되지 않은 사용자 접근 시도");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("인증되지 않은 사용자입니다.");
            }

            // SecurityContext에서 이메일 가져오기
            String email = authentication.getName();
            log.info("요청한 사용자 이메일: {}", email);

            // 이메일로 사용자 조회 후 학번 가져오기
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                log.warn("사용자 정보를 찾을 수 없음: {}", email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("사용자 정보를 찾을 수 없습니다.");
            }

            String studentId = user.get().getStudentId();
            log.info("요청한 사용자 학번: {}", studentId);

            // 학번을 기반으로 결과 조회
            ResultDto resultDto = userPageService.getResultByStudent(studentId);
            return ResponseEntity.ok(resultDto);

        } catch (IllegalArgumentException e) {
          log.warn("결과 조회 기간이 아님: ", e);
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결과 조회 기간이 아닙니다.");
        } catch (Exception e) {
            log.error("유저 결과 조회 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류가 발생했습니다.");
        }
    }
}
