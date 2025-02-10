package com.springboot.jwt.service;

import com.springboot.jwt.entity.Email;
import com.springboot.jwt.repository.EmailRepository;
import com.springboot.jwt.util.RedisUtil;
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

    // 인증 코드 생성 (6자리 무작위 문자열)
    private String createCode() {
        int leftLimit = 48; // 숫자 '0'
        int rightLimit = 122; // 알파벳 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // 이메일 템플릿 내용을 생성 (Thymeleaf 사용)
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

    // 이메일 유효성 검증
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }

    /**
     * 이메일 폼을 생성하여 발송합니다.
     * 만약 이메일에 "@"가 없으면 '@kangnam.ac.kr'을 추가하고,
     * 이메일 문자열을 trim() 및 toLowerCase()로 정제하여 Redis 키로 일관되게 사용합니다.
     */
    public void sendEmail(String toEmail, String customMessage) throws MessagingException {
        // 이메일에 도메인이 없으면 추가
        if (!toEmail.contains("@")) {
            toEmail = toEmail + "@kangnam.ac.kr";
        }
        // 이메일 정제 (앞뒤 공백 제거 및 소문자 변환)
        toEmail = toEmail.trim().toLowerCase();

        if (!isValidEmail(toEmail)) {
            throw new IllegalArgumentException("유효하지 않은 이메일 주소입니다: " + toEmail);
        }

        // 기존에 Redis에 저장된 인증 코드가 있다면 삭제
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }
        // 이메일 폼 생성
        MimeMessage emailForm = createEmailForm(toEmail, customMessage);
        // 이메일 발송
        javaMailSender.send(emailForm);
    }

    // 기본 인증 메시지를 사용하는 오버로딩 메서드
    public void sendEmail(String toEmail) throws MessagingException {
        sendEmail(toEmail, "기본 인증 메시지입니다.");
    }

    // 이메일 폼 생성 (Redis에 인증 코드를 저장하고 로그로 출력)
    private MimeMessage createEmailForm(String email, String customMessage) throws MessagingException {
        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setTo(email);
        helper.setSubject("안녕하세요. 강남대학교 멋쟁이사자처럼입니다.");
        helper.setFrom(senderEmail);
        helper.setText(setContext(authCode, customMessage), true);

        // Redis에 인증 코드 저장 (10분 유효)
        redisUtil.setDataExpire(email, authCode, 60 * 10L);
        log.info("Redis에 저장된 키: {}, 값: {}", email, authCode);

        return message;
    }

    /**
     * 이메일 인증 코드 검증 메서드.
     * 전달된 이메일을 정제한 후 Redis에서 인증 코드를 조회하고,
     * 코드가 일치하면 Email 엔티티를 DB에 저장합니다.
     */
    @Transactional
    public Boolean verifyEmailCode(String email, String code) {
        // 이메일에 도메인이 없으면 추가
        if (!email.contains("@")) {
            email = email + "@kangnam.ac.kr";
        }
        // 이메일 정제 (앞뒤 공백 제거 및 소문자 변환)
        email = email.trim().toLowerCase();

        log.info("Redis에서 조회할 키: {}", email);
        String codeFoundByEmail = redisUtil.getData(email);
        log.info("Redis에서 조회된 인증 코드: {}", codeFoundByEmail);

        if (codeFoundByEmail == null) {
            log.error("Redis에 인증 코드가 없습니다. (키: {})", email);
            return false;
        }
        if (codeFoundByEmail.equals(code)) {
            log.info("인증 성공. 이메일 DB에 저장합니다.");

            // Email DB에 저장 (이미 존재하는지 체크)
            Optional<Email> emailEntity = emailRepository.findByEmail(email);
            if (emailEntity.isEmpty()) {
                Email newEmail = Email.builder()
                        .email(email)
                        .isVerified(true)
                        .createdAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusDays(1)) // 만료일은 예시로 1일 설정
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