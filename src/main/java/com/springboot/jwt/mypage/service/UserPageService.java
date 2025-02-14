package com.springboot.jwt.mypage.service;

import com.springboot.jwt.login.config.UserRole;
import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.repository.UserRepository;
import com.springboot.jwt.mypage.dto.ChangeInformationRequest;
import com.springboot.jwt.mypage.dto.ResultDto;
import com.springboot.jwt.result.entity.Result;
import com.springboot.jwt.result.repository.ResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPageService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ResultRepository resultRepository;

    // 이메일로 사용자 정보 조회
    public Optional<User> getLoginUserByLoginId(String email) {
        return userRepository.findByEmail(email);
    }

    // 유저 마이페이지 정보 수정
    public User changeInformation(User user, ChangeInformationRequest changeInformationRequest) {
        // 유저 정보 업데이트
        if (changeInformationRequest.getName() == null || changeInformationRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 비워둘 수 없습니다.");
        }
        if (changeInformationRequest.getDepartment() == null || changeInformationRequest.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("학부(전공)는 비워둘 수 없습니다.");
        }
        if (changeInformationRequest.getStudentId() == null || changeInformationRequest.getStudentId().trim().isEmpty()) {
            throw new IllegalArgumentException("학번은 비워둘 수 없습니다.");
        }
        if (changeInformationRequest.getGrade() == null || changeInformationRequest.getGrade().trim().isEmpty()) {
            throw new IllegalArgumentException("학년은 비워둘 수 없습니다.");
        }
        if (changeInformationRequest.getPhone() == null || changeInformationRequest.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("전화번호는 비워둘 수 없습니다.");
        }

        // 비밀번호가 비어있지 않은지 확인
        if (changeInformationRequest.getPassword() != null && !changeInformationRequest.getPassword().trim().isEmpty()) {
            // 비밀번호 확인
            if (!changeInformationRequest.getPassword().equals(changeInformationRequest.getPasswordCheck())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

            // 기존 비밀번호와 비교
            if (bCryptPasswordEncoder.matches(changeInformationRequest.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("기존 비밀번호와 같습니다.");
            }
            // 새 비밀번호 암호화하여 설정
            user.setPassword(bCryptPasswordEncoder.encode(changeInformationRequest.getPassword()));
        } else {
            throw new IllegalArgumentException("비밀번호는 비워둘 수 없습니다.");
        }

        // 유저 정보 갱신
        user.setName(changeInformationRequest.getName());
        user.setDepartment(changeInformationRequest.getDepartment());
        user.setStudentId(changeInformationRequest.getStudentId());
        user.setGrade(changeInformationRequest.getGrade());
        user.setPhone(changeInformationRequest.getPhone());

        return userRepository.save(user);
    }

    // 권한 변경
    @Transactional
    public void changeUserRoleByEmail(String email, UserRole newRole) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("이메일을 찾을 수 없습니다."));

        user.setRole(newRole); // 권한 변경
        userRepository.save(user);
    }

    // 유저 합불 조회 (결과보기)
    public ResultDto getResultByStudent(String StudentId) {
        User user = userRepository.findByStudentId(StudentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번 유저 찾을 수 없습니다."));

        Result result = resultRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("결과 조회 기간이 아닙니다."));

        return new ResultDto(user.getName(), result.getResultStatus());
    }
}

