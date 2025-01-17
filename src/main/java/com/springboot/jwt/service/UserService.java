package com.springboot.jwt.service;

import com.springboot.jwt.dto.JoinRequest;
import com.springboot.jwt.dto.LoginRequest;
import com.springboot.jwt.entity.Email;
import com.springboot.jwt.entity.User;
import com.springboot.jwt.repository.EmailRepository;
import com.springboot.jwt.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 이메일 중복 확인
    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    // 학번 중복 확인
    public boolean checkSchoolNumDuplicate(String schoolId) {
        return userRepository.existsBystudentId(schoolId);
    }

    // 전화번호 중복 확인
    public boolean checkPhoneNumDuplicate(String phone) {
        return userRepository.existsByPhone(phone);
    }

    // 이메일 인증 여부 확인
    public boolean isEmailVerified(String email) {
        Optional<com.springboot.jwt.entity.Email> emailOptional = emailRepository.findByEmail(email);
        return emailOptional.isPresent() && emailOptional.get().isVerified();
    }

    // 회원가입
    @Transactional
    public void join(JoinRequest joinRequest) {
        if (joinRequest.getEmail() != null && !joinRequest.getEmail().contains("@")) {
            joinRequest.setEmail(joinRequest.getEmail() + "@kangnam.ac.kr");
        }
        // 이메일 중복 확인
        if (userRepository.existsByEmail(joinRequest.getEmail())) {
            throw new IllegalArgumentException("이메일이 중복됩니다.");
        }

        // 이메일 인증 여부 확인
        Optional<Email> emailVerification = emailRepository.findByEmail(joinRequest.getEmail());
        if (emailVerification.isEmpty() || !emailVerification.get().isVerified()) {
            throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
        }
        // 비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(joinRequest.getPassword());

        // 엔티티 생성
        User user = joinRequest.toEntity(encodedPassword);

        // 유저 저장
        userRepository.save(user);

        // 이메일 인증 데이터 삭제
        emailRepository.deleteByEmail(joinRequest.getEmail());
    }

    // 이메일 인증 요청
    public void sendEmailVerification(String email) throws MessagingException {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String verificationCode = generateVerificationCode();
        Email emailEntity = emailRepository.findByEmail(email).orElse(new Email());

        emailEntity.setEmail(email);
        emailEntity.setVerificationCode(verificationCode);
        emailEntity.setCreatedAt(LocalDateTime.now());
        emailEntity.setExpiresAt(LocalDateTime.now().plusMinutes(10)); // 인증 코드 유효 시간 10분
        emailEntity.setVerified(false);

        emailRepository.save(emailEntity);

        emailService.sendEmail(email, "이메일 인증 코드: " + verificationCode);
    }

    // 이메일 인증 검증
    public boolean verifyEmail(String email, String code) {
        Optional<Email> emailOptional = emailRepository.findByEmailAndVerificationCode(email, code);

        if (emailOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 인증 코드입니다.");
        }

        Email emailEntity = emailOptional.get();
        if (emailEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증 코드가 만료되었습니다.");
        }

        emailEntity.setVerified(true); // 인증 완료
        emailRepository.save(emailEntity);

        return true;
    }

    public User login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByEmail(req.getEmail());

        // email과 일치하는 User가 없으면 null return
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        // 비밀번호가 일치하지 않으면 null return
        if (!bCryptPasswordEncoder.matches(req.getPassword(), user.getPassword())) {
            return null;
        }

        return user;
    }
    // UserService 클래스에 추가
    public Optional<User> getLoginUserByLoginId(String loginId) {
        return userRepository.findByEmail(loginId); // 혹은 적절한 필드로 수정
    }

    private String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 8); // 8자리 코드 생성
    }
}