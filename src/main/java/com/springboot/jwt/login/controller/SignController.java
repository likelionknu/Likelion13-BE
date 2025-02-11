package com.springboot.jwt.login.controller;

import com.springboot.jwt.login.dto.EmailDto;
import com.springboot.jwt.login.dto.JoinRequest;
import com.springboot.jwt.login.dto.LoginRequest;
import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.jwt.JwtTokenUtil;
import com.springboot.jwt.login.service.EmailService;
import com.springboot.jwt.login.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SignController {
    private final UserService userService;
    private final EmailService emailService;

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
    public String login(@RequestBody LoginRequest loginRequest) {
        try {
            // 사용자 로그인 로직 호출
            User user = userService.login(loginRequest);

            // 로그인 실패: 이메일 또는 비밀번호가 잘못된 경우
            if (user == null) {
                return "이메일 또는 비밀번호가 틀렸습니다.";
            }

            // 로그인 성공: JWT 토큰 발급
            String secretKey = "YXYtdmVyeS1sb25nLXNlY3JldC1rZXktd2hpY2gtaXM=12341234"; // 비밀 키
            long expireTimeMs = 1000 * 60 * 60; // 토큰 유효 시간 (60분)

            // JWT 토큰 생성
            String jwtToken = JwtTokenUtil.createToken(user.getEmail(), user.getRole(), secretKey, expireTimeMs);
            return jwtToken;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}