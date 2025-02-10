package com.springboot.jwt.controller;

import com.springboot.jwt.dto.EmailDto;
import com.springboot.jwt.dto.JoinRequest;
import com.springboot.jwt.dto.LoginRequest;
import com.springboot.jwt.entity.User;
import com.springboot.jwt.jwt.JwtTokenUtil;
import com.springboot.jwt.service.EmailService;
import com.springboot.jwt.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:3000", "https://likelion13-fe.vercel.app"})
public class SignController {
    private final UserService userService;
    private final EmailService emailService;
    private final UserService.TokenBlacklistService tokenBlacklistService;
    private final JwtTokenUtil jwtTokenUtil;

    // 인증코드 발송
    @PostMapping("/send")
    public String mailSend(EmailDto emailDto) throws MessagingException {
        log.info("EmailController.mailSend()");
        emailService.sendEmail(emailDto.getEmail());
        return "인증코드가 발송되었습니다.";
    }

    // 인증코드 인증
    @PostMapping("/verify")
    public String verify(EmailDto emailDto) {
        log.info("EmailController.verify()");
        boolean isVerify = emailService.verifyEmailCode(emailDto.getEmail(), emailDto.getVerifyCode());
        return isVerify ? "인증이 완료되었습니다." : "인증 실패하셨습니다.";
    }

    // 회원가입
    @PostMapping("/sign-up")
    public String join(@RequestBody JoinRequest joinRequest) {
        if (joinRequest.getEmail() != null && !joinRequest.getEmail().contains("@")) {
            joinRequest.setEmail(joinRequest.getEmail() + "@kangnam.ac.kr");
        }
        try {
            // 이메일 중복 체크
            if (userService.checkEmailDuplicate(joinRequest.getEmail())) {
                return "이메일이 중복됩니다.";
            }
            // 학번 중복 체크
            if (userService.checkSchoolNumDuplicate(joinRequest.getStudentId())) {
                return "학번이 중복됩니다.";
            }
            // 전화번호 중복 체크
            if (userService.checkPhoneNumDuplicate(joinRequest.getPhone())) {
                return "전화번호가 중복됩니다.";
            }
            // 비밀번호 확인
            if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
                return "비밀번호가 일치하지 않습니다.";
            }
            // 이메일 인증 여부 확인
            if (!userService.isEmailVerified(joinRequest.getEmail())) {
                return "이메일 인증이 완료되지 않았습니다.";
            }
            userService.join(joinRequest);
            return "회원가입이 완료되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 사용자 로그인 로직 호출
            User user = userService.login(loginRequest);

            // 로그인 실패: 이메일 또는 비밀번호가 잘못된 경우
            if (user == null) {
                return ResponseEntity.badRequest().body("이메일 또는 비밀번호가 틀렸습니다.");
            }

            long expireTimeMs = 1000 * 60 * 60; // 토큰 유효 시간 (60분)

            // JWT 토큰 생성
            String jwtToken = jwtTokenUtil.createToken(user.getEmail(), expireTimeMs);
            // 토큰 생성 직후 로그 출력
            log.info("생성된 JWT: " + jwtToken);

            return ResponseEntity.ok(jwtToken);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String jwtToken) {
        try {
            if (jwtToken == null || jwtToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰이 제공되지 않았습니다.");
            }

            // 토큰 블랙리스트에 추가
            tokenBlacklistService.addToBlacklist(jwtToken);

            return ResponseEntity.ok("로그아웃되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody String jwtToken) {
        try {
            if (jwtToken == null || jwtToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰이 제공되지 않았습니다.");
            }

            // 불필요한 따옴표나 공백 제거 및 로그 출력
            jwtToken = jwtToken.replaceAll("^\"|\"$", "").trim();
            log.info("검증 전 JWT: " + jwtToken);

            String email = jwtTokenUtil.getLoginId(jwtToken);
            log.info("Extracted email from JWT: " + email);

            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
            }

            if (jwtTokenUtil.isExpired(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다.");
            }

            boolean deleted = userService.deleteByEmail(email);

            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
            }

            // 토큰 블랙리스트에 추가
            tokenBlacklistService.addToBlacklist(jwtToken);
            log.info("Token added to blacklist: " + jwtToken);

            return ResponseEntity.ok("계정이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("계정 삭제 중 오류가 발생했습니다. " + e.getMessage());
        }
    }
}
