package com.springboot.jwt.resume.service.Impl;

import com.springboot.jwt.resume.dto.BackendResumeRequestDto;
import com.springboot.jwt.resume.dto.DesignResumeRequestDto;
import com.springboot.jwt.resume.entity.BackendResume;
import com.springboot.jwt.resume.entity.DesignResume;
import com.springboot.jwt.resume.repository.DesignResumeRepository;
import com.springboot.jwt.resume.service.DesignResumeService;
import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DesignResumeServiceImpl implements DesignResumeService {
    private final DesignResumeRepository designResumeRepository;
    private final UserRepository userRepository;

    public DesignResumeServiceImpl(DesignResumeRepository designResumeRepository, UserRepository userRepository) {
        this.designResumeRepository = designResumeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void designCreateResume(DesignResumeRequestDto designResumeRequestDto) {
        User user = userRepository.findByStudentId(designResumeRequestDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자(User)를 찾을 수 없습니다."));

        if (user.isApply()) {
            throw new IllegalArgumentException("하나의 파트만 지원이 가능합니다.");
        }

        DesignResume designResume = designResumeRepository.findByUser(user)
                .orElseGet(DesignResume::new);

        designResume.setUser(user);
        designResume.setName(designResumeRequestDto.getName());
        designResume.setDesigncontent1(designResumeRequestDto.getDesigncontent1());
        designResume.setDesigncontent2(designResumeRequestDto.getDesigncontent2());
        designResume.setDesigncontent3(designResumeRequestDto.getDesigncontent3());
        designResume.setDesigncontent4(designResumeRequestDto.getDesigncontent4());
        designResume.setDesigncontent5(designResumeRequestDto.getDesigncontent5());
        designResume.setDesigncontent6(designResumeRequestDto.getDesigncontent6());
        designResume.setDesigncontent7(designResumeRequestDto.getDesigncontent7());
        designResume.setDesigncontent8(designResumeRequestDto.getDesigncontent8());
        designResume.setDesigncontent9(designResumeRequestDto.getDesigncontent9());

        designResumeRepository.save(designResume);
    }


    @Override
    public DesignResumeRequestDto designGetResumeById(Long id) {
        return designResumeRepository.findById(id)
                .map(designResume -> new DesignResumeRequestDto(
                        designResume.getId(),
                        designResume.getUser() != null ? designResume.getUser().getStudentId() : null,
                        designResume.getName(),
                        designResume.getDesigncontent1(),
                        designResume.getDesigncontent2(),
                        designResume.getDesigncontent3(),
                        designResume.getDesigncontent4(),
                        designResume.getDesigncontent5(),
                        designResume.getDesigncontent6(),
                        designResume.getDesigncontent7(),
                        designResume.getDesigncontent8(),
                        designResume.getDesigncontent9(),
                        designResume.isApply()
                ))
                .orElseThrow(() -> new RuntimeException("지원서를 찾을 수 없습니다."));
    }

    @Override
    public void designUpdateResume(Long id, DesignResumeRequestDto designResumeRequestDto) {
        DesignResume designResume = designResumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("지원서를 찾을 수 없습니다."));

        User user = userRepository.findByStudentId(designResumeRequestDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("해당 사용자(User)를 찾을 수 없습니다."));

        designResume.setUser(user);
        designResume.setName(designResumeRequestDto.getName());
        designResume.setDesigncontent1(designResumeRequestDto.getDesigncontent1());
        designResume.setDesigncontent2(designResumeRequestDto.getDesigncontent2());
        designResume.setDesigncontent3(designResumeRequestDto.getDesigncontent3());
        designResume.setDesigncontent4(designResumeRequestDto.getDesigncontent4());
        designResume.setDesigncontent5(designResumeRequestDto.getDesigncontent5());
        designResume.setDesigncontent6(designResumeRequestDto.getDesigncontent6());
        designResume.setDesigncontent7(designResumeRequestDto.getDesigncontent7());
        designResume.setDesigncontent8(designResumeRequestDto.getDesigncontent8());
        designResume.setDesigncontent9(designResumeRequestDto.getDesigncontent9());

        designResumeRepository.save(designResume);
    }

    @Transactional(readOnly = true)
    public List<DesignResumeRequestDto> findByApply(boolean apply) {
        List<DesignResume> designResumes = designResumeRepository.findAllByApply(apply);
        List<DesignResumeRequestDto> ResumedtoList = designResumes.stream()
                .map(designResume -> {
                    DesignResumeRequestDto designResumeRequestDto = DesignResumeRequestDto.fromEntity(designResume);
                    return designResumeRequestDto;
                })
                .collect(Collectors.toList());
        return ResumedtoList;
    }

    public DesignResumeRequestDto updateResumeStatus(Long id, boolean apply) {
        DesignResume designResume = designResumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("[error] 해당 지원서를 찾을 수 없습니다."));
        designResume.setApply(apply);

        // user의 apply 상태 변경
        User user = designResume.getUser();
        if (user != null) {
            user.setApply(apply);
            userRepository.save(user);
        }
        designResumeRepository.save(designResume);
        return DesignResumeRequestDto.fromEntity(designResume);
    }
}