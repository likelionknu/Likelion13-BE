package com.springboot.jwt.resume.service.Impl;

import com.springboot.jwt.resume.dto.FrontendResumeRequestDto;
import com.springboot.jwt.resume.entity.FrontendResume;
import com.springboot.jwt.resume.repository.FrontendResumeRepository;
import com.springboot.jwt.resume.service.FrontendResumeService;
import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FrontendResumeServiceImpl implements FrontendResumeService {
    private final FrontendResumeRepository frontendResumeRepository;
    private final UserRepository userRepository;

    public FrontendResumeServiceImpl(FrontendResumeRepository frontendResumeRepository, UserRepository userRepository) {
        this.frontendResumeRepository = frontendResumeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void frontendCreateResume(FrontendResumeRequestDto frontendResumeRequestDto) {
        User user = userRepository.findById(frontendResumeRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 사용자(User)를 찾을 수 없습니다."));

        FrontendResume frontendResume = new FrontendResume();
        frontendResume.setUser(user);
        frontendResume.setName(frontendResumeRequestDto.getName());
        frontendResume.setFrontendcontent1(frontendResumeRequestDto.getFrontendcontent1());
        frontendResume.setFrontendcontent2(frontendResumeRequestDto.getFrontendcontent2());
        frontendResume.setFrontendcontent3(frontendResumeRequestDto.getFrontendcontent3());
        frontendResume.setFrontendcontent4(frontendResumeRequestDto.getFrontendcontent4());
        frontendResume.setFrontendcontent5(frontendResumeRequestDto.getFrontendcontent5());
        frontendResume.setFrontendcontent6(frontendResumeRequestDto.getFrontendcontent6());
        frontendResume.setFrontendcontent7(frontendResumeRequestDto.getFrontendcontent7());
        frontendResume.setFrontendcontent8(frontendResumeRequestDto.getFrontendcontent8());
        frontendResume.setFrontendcontent9(frontendResumeRequestDto.getFrontendcontent9());

        frontendResumeRepository.save(frontendResume);
    }

    @Override
    public FrontendResumeRequestDto frontendGetResumeById(Long id) {
        return frontendResumeRepository.findById(id)
                .map(frontendResume -> new FrontendResumeRequestDto(
                        frontendResume.getId(),
                        frontendResume.getUser() != null ? frontendResume.getUser().getId() : null,
                        frontendResume.getName(),
                        frontendResume.getFrontendcontent1(),
                        frontendResume.getFrontendcontent2(),
                        frontendResume.getFrontendcontent3(),
                        frontendResume.getFrontendcontent4(),
                        frontendResume.getFrontendcontent5(),
                        frontendResume.getFrontendcontent6(),
                        frontendResume.getFrontendcontent7(),
                        frontendResume.getFrontendcontent8(),
                        frontendResume.getFrontendcontent9()
                ))
                .orElseThrow(() -> new RuntimeException("지원서를 찾을 수 없습니다."));
    }

    @Override
    public void frontendUpdateResume(Long id, FrontendResumeRequestDto frontendResumeRequestDto) {
        FrontendResume frontendResume = frontendResumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("지원서를 찾을 수 없습니다."));

        User user = userRepository.findById(frontendResumeRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 사용자(User)를 찾을 수 없습니다."));

        frontendResume.setUser(user);
        frontendResume.setName(frontendResumeRequestDto.getName());
        frontendResume.setFrontendcontent1(frontendResumeRequestDto.getFrontendcontent1());
        frontendResume.setFrontendcontent2(frontendResumeRequestDto.getFrontendcontent2());
        frontendResume.setFrontendcontent3(frontendResumeRequestDto.getFrontendcontent3());
        frontendResume.setFrontendcontent4(frontendResumeRequestDto.getFrontendcontent4());
        frontendResume.setFrontendcontent5(frontendResumeRequestDto.getFrontendcontent5());
        frontendResume.setFrontendcontent6(frontendResumeRequestDto.getFrontendcontent6());
        frontendResume.setFrontendcontent7(frontendResumeRequestDto.getFrontendcontent7());
        frontendResume.setFrontendcontent8(frontendResumeRequestDto.getFrontendcontent8());
        frontendResume.setFrontendcontent9(frontendResumeRequestDto.getFrontendcontent9());

        frontendResumeRepository.save(frontendResume);
    }
}
