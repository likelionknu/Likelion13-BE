package com.springboot.jwt.resume.service.Impl;

import com.springboot.jwt.resume.dto.ResumeRequestDto;
import com.springboot.jwt.resume.entity.Resume;
import com.springboot.jwt.resume.repository.ResumeRepository;
import com.springboot.jwt.resume.service.ResumeService;
import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional // 🔴 추가: 트랜잭션을 적용하여 DB 변경을 안전하게 처리
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository, UserRepository userRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createResume(ResumeRequestDto resumeRequestDto) {
        User user = userRepository.findById(resumeRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 사용자(User)를 찾을 수 없습니다."));

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setName(resumeRequestDto.getName());
        resume.setContent1(resumeRequestDto.getContent1());
        resume.setContent2(resumeRequestDto.getContent2());
        resume.setContent3(resumeRequestDto.getContent3());
        resume.setContent4(resumeRequestDto.getContent4());
        resume.setContent5(resumeRequestDto.getContent5());
        resume.setContent6(resumeRequestDto.getContent6());
        resume.setContent7(resumeRequestDto.getContent7());
        resume.setContent8(resumeRequestDto.getContent8());
        resume.setContent9(resumeRequestDto.getContent9());
        resume.setContent10(resumeRequestDto.getContent10());

        resumeRepository.save(resume);
    }

    @Override
    public ResumeRequestDto getResumeById(Long id) {
        return resumeRepository.findById(id)
                .map(resume -> new ResumeRequestDto(
                        resume.getId(),
                        resume.getUser() != null ? resume.getUser().getId() : null,  // 🔴 수정: user가 null일 경우를 고려하여 안전한 접근
                        resume.getName(),
                        resume.getContent1(),
                        resume.getContent2(),
                        resume.getContent3(),
                        resume.getContent4(),
                        resume.getContent5(),
                        resume.getContent6(),
                        resume.getContent7(),
                        resume.getContent8(),
                        resume.getContent9(),
                        resume.getContent10(),
                        resume.isApply()
                ))
                .orElseThrow(() -> new RuntimeException("지원서를 찾을 수 없습니다."));
    }

    @Override
    public void updateResume(Long id, ResumeRequestDto resumeRequestDto) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("지원서를 찾을 수 없습니다."));

        User user = userRepository.findById(resumeRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 사용자(User)를 찾을 수 없습니다."));

        resume.setUser(user);
        resume.setName(resumeRequestDto.getName());
        resume.setContent1(resumeRequestDto.getContent1());
        resume.setContent2(resumeRequestDto.getContent2());
        resume.setContent3(resumeRequestDto.getContent3());
        resume.setContent4(resumeRequestDto.getContent4());
        resume.setContent5(resumeRequestDto.getContent5());
        resume.setContent6(resumeRequestDto.getContent6());
        resume.setContent7(resumeRequestDto.getContent7());
        resume.setContent8(resumeRequestDto.getContent8());
        resume.setContent9(resumeRequestDto.getContent9());
        resume.setContent10(resumeRequestDto.getContent10());

        resumeRepository.save(resume);
    }

    // 특정 상태 지원서 조회 (apply = True 일 시 지원 완료)
    @Override
    @Transactional(readOnly = true)
    public List<ResumeRequestDto> findByApply(boolean apply) {
        List<Resume> resumes = resumeRepository.findAllByApply(apply);
        List<ResumeRequestDto> ResumedtoList = resumes.stream()
                .map(resume -> {
                    ResumeRequestDto resumeRequestDto = ResumeRequestDto.fromEntity(resume);
                    return resumeRequestDto;
                })
                .collect(Collectors.toList());
        return ResumedtoList;
    }

    public ResumeRequestDto updateResumeStatus(Long id, boolean apply) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("[error] 해당 지원서를 찾을 수 없습니다."));
        resume.setApply(apply);

        // user의 apply 상태 변경
        User user = resume.getUser();
        if (user != null) {
            user.setApply(apply);
            userRepository.save(user);
        }
        resumeRepository.save(resume);
        return ResumeRequestDto.fromEntity(resume);
    }
}