package com.springboot.jwt.resume.service.Impl;

import com.springboot.jwt.resume.dto.BackendResumeRequestDto;
import com.springboot.jwt.resume.entity.BackendResume;
import com.springboot.jwt.resume.repository.BackendResumeRepository;
import com.springboot.jwt.resume.service.BackendResumeService;
import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BackendResumeServiceImpl implements BackendResumeService {
    private final BackendResumeRepository backendResumeRepository;
    private final UserRepository userRepository;

    public BackendResumeServiceImpl(BackendResumeRepository backendResumeRepository, UserRepository userRepository) {
        this.backendResumeRepository = backendResumeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void backendCreateResume(BackendResumeRequestDto backendResumeRequestDto) {
        User user = userRepository.findByStudentId(backendResumeRequestDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자(User)를 찾을 수 없습니다."));

        if (user.isApply()) {
            throw new IllegalArgumentException("하나의 파트만 지원이 가능합니다.");
        }

        BackendResume backendResume = new BackendResume();
        backendResume.setUser(user);
        backendResume.setName(backendResumeRequestDto.getName());
        backendResume.setBackendcontent1(backendResumeRequestDto.getBackendcontent1());
        backendResume.setBackendcontent2(backendResumeRequestDto.getBackendcontent2());
        backendResume.setBackendcontent3(backendResumeRequestDto.getBackendcontent3());
        backendResume.setBackendcontent4(backendResumeRequestDto.getBackendcontent4());
        backendResume.setBackendcontent5(backendResumeRequestDto.getBackendcontent5());
        backendResume.setBackendcontent6(backendResumeRequestDto.getBackendcontent6());
        backendResume.setBackendcontent7(backendResumeRequestDto.getBackendcontent7());
        backendResume.setBackendcontent8(backendResumeRequestDto.getBackendcontent8());
        backendResume.setBackendcontent9(backendResumeRequestDto.getBackendcontent9());
        backendResume.setBackendcontent10(backendResumeRequestDto.getBackendcontent10());

        backendResumeRepository.save(backendResume);
    }


    @Override
    public BackendResumeRequestDto backendGetResumeById(Long id) {
        return backendResumeRepository.findById(id)
                .map(backendResume -> new BackendResumeRequestDto(
                        backendResume.getId(),
                        backendResume.getUser() != null ? backendResume.getUser().getStudentId() :  null,
                        backendResume.getName(),
                        backendResume.getBackendcontent1(),
                        backendResume.getBackendcontent2(),
                        backendResume.getBackendcontent3(),
                        backendResume.getBackendcontent4(),
                        backendResume.getBackendcontent5(),
                        backendResume.getBackendcontent6(),
                        backendResume.getBackendcontent7(),
                        backendResume.getBackendcontent8(),
                        backendResume.getBackendcontent9(),
                        backendResume.getBackendcontent10(),
                        backendResume.isApply()
                ))
                .orElseThrow(() -> new RuntimeException("지원서를 찾을 수 없습니다."));
    }

    @Override
    public void backendUpdateResume(Long id, BackendResumeRequestDto backendResumeRequestDto) {
        BackendResume backendResume = backendResumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("지원서를 찾을 수 없습니다."));

        User user = userRepository.findByStudentId(backendResumeRequestDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("해당 사용자(User)를 찾을 수 없습니다."));

        backendResume.setUser(user);
        backendResume.setName(backendResumeRequestDto.getName());
        backendResume.setBackendcontent1(backendResumeRequestDto.getBackendcontent1());
        backendResume.setBackendcontent2(backendResumeRequestDto.getBackendcontent2());
        backendResume.setBackendcontent3(backendResumeRequestDto.getBackendcontent3());
        backendResume.setBackendcontent4(backendResumeRequestDto.getBackendcontent4());
        backendResume.setBackendcontent5(backendResumeRequestDto.getBackendcontent5());
        backendResume.setBackendcontent6(backendResumeRequestDto.getBackendcontent6());
        backendResume.setBackendcontent7(backendResumeRequestDto.getBackendcontent7());
        backendResume.setBackendcontent8(backendResumeRequestDto.getBackendcontent8());
        backendResume.setBackendcontent9(backendResumeRequestDto.getBackendcontent9());
        backendResume.setBackendcontent10(backendResumeRequestDto.getBackendcontent10());

        backendResumeRepository.save(backendResume);
    }


    // 특정 상태 지원서 조회 (apply = True 일 시 지원 완료)
    //@Override
    @Transactional(readOnly = true)
    public List<BackendResumeRequestDto> findByApply(boolean apply) {
        List<BackendResume> backendResumes = backendResumeRepository.findAllByApply(apply);
        List<BackendResumeRequestDto> ResumedtoList = backendResumes.stream()
                .map(backendResume -> {
                    BackendResumeRequestDto backendResumeRequestDto = BackendResumeRequestDto.fromEntity(backendResume);
                    return backendResumeRequestDto;
                })
                .collect(Collectors.toList());
        return ResumedtoList;
    }

    public BackendResumeRequestDto updateResumeStatus(Long id, boolean apply) {
        BackendResume backendResume = backendResumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("[error] 해당 지원서를 찾을 수 없습니다."));
        backendResume.setApply(apply);

        // user의 apply 상태 변경
        User user = backendResume.getUser();
        if (user != null) {
            user.setApply(apply);
            userRepository.save(user);
        }
        backendResumeRepository.save(backendResume);
        return BackendResumeRequestDto.fromEntity(backendResume);
    }
}