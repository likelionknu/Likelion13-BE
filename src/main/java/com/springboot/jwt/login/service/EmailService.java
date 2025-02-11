package com.springboot.jwt.login.service;

import com.springboot.jwt.login.entity.Email;
import com.springboot.jwt.login.repository.EmailRepository;
import com.springboot.jwt.login.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final EmailRepository emailRepository;
    private static final String senderEmail = "sanbyul1@naver.com";

    private String createCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 | i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // 이메일 내용 초기화
    private String setContext(String authCode, String customMessage) {
        Context context = new Context();
        context.setVariable("code", authCode);
        context.setVariable("customMessage", customMessage);

        // Template Resolver 설정
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);

        // Template Engine 초기화
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine.process("mail", context);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }

    // 이메일 폼 생성
    public void sendEmail(String toEmail, String customMessage) throws MessagingException {
        if (!toEmail.contains("@")) {
            toEmail = toEmail + "@kangnam.ac.kr"; // 도메인을 추가
        }

        if (!isValidEmail(toEmail)) {
            throw new IllegalArgumentException("유효하지 않은 이메일 주소입니다: " + toEmail);
        }

        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }
        // 이메일 폼 생성
        MimeMessage emailForm = createEmailForm(toEmail, customMessage);
        // 이메일 발송
        javaMailSender.send(emailForm);
    }

    // 오버로딩 메소드 추가 (선택 사항)
    public void sendEmail(String toEmail) throws MessagingException {
        sendEmail(toEmail, "기본 인증 메시지입니다.");
    }

    // 이메일 폼 생성 메소드 수정
    private MimeMessage createEmailForm(String email, String customMessage) throws MessagingException {
        String authCode = createCode();

        // 인증코드 로그 출력
        log.info("발송된 인증코드 [{}] -> {}", authCode, email);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setTo(email);
        helper.setSubject("안녕하세요. 강남대학교 멋쟁이사자처럼입니다.");
        helper.setFrom(senderEmail);
        helper.setText(setContext(authCode, customMessage), true);

        // Redis에 인증 코드 저장
        redisUtil.setDataExpire(email, authCode, 60 * 10L);

        return message;
    }

    @Transactional
    public Boolean verifyEmailCode(String email, String code) {

        if (!email.contains("@")) {
            email = email + "@kangnam.ac.kr"; // 도메인을 추가
        }

        String codeFoundByEmail = redisUtil.getData(email);
        log.info("code found by email: " + codeFoundByEmail);
        if (codeFoundByEmail == null) {
            log.error("Redis에 인증 코드가 없습니다.");
            return false;
        }
        if (codeFoundByEmail.equals(code)) {
            log.info("인증 성공. 이메일 DB에 저장합니다.");

            // Email DB에 저장
            Optional<Email> emailEntity = emailRepository.findByEmail(email);
            if (emailEntity.isEmpty()) {
                Email newEmail = Email.builder()
                        .email(email)
                        .isVerified(true)
                        .createdAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusDays(1)) // 만료일은 예시
                        .build();
                emailRepository.save(newEmail);
                log.info("이메일 인증 정보가 저장되었습니다.");
            } else {
                log.info("이미 존재하는 이메일입니다.");
            }
            return true;
        }
        log.error("인증 코드가 일치하지 않습니다.");
        return false;
    }
}
