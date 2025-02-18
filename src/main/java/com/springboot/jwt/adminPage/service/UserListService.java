package com.springboot.jwt.adminPage.service;

import com.springboot.jwt.adminPage.dto.UserListDto;
import com.springboot.jwt.result.repository.ResultRepository;
import com.springboot.jwt.result.service.ResultService;
import com.springboot.jwt.resume.entity.BackendResume;
import com.springboot.jwt.resume.entity.DesignResume;
import com.springboot.jwt.resume.entity.FrontendResume;
import com.springboot.jwt.resume.repository.BackendResumeRepository;
import com.springboot.jwt.resume.repository.DesignResumeRepository;
import com.springboot.jwt.resume.repository.FrontendResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserListService {
    private final ResultRepository resultRepository;
    private final DesignResumeRepository designResumeRepository;
    private final BackendResumeRepository backendResumeRepository;
    private final FrontendResumeRepository frontendResumeRepository;

    /* 지원자 전체 */
    public List<UserListDto> getUserList(){
        return resultRepository.findAll().stream()
                .map(result -> new UserListDto(
                        result.getUser().getName(),
                        result.getUser().getStudentId(),
                        result.getUser().getPhone(),
                        result.getUser().getDepartment(),
                        result.getResultStatus()
                ))
                .collect(Collectors.toList());
    }

    /* 백엔드 지원한 유저 */
    public List<UserListDto> getBackendUserList(){
        return backendResumeRepository.findAll().stream()
                .filter(BackendResume::isApply)
                .map(result -> new UserListDto(
                        result.getUser().getName(),
                        result.getUser().getStudentId(),
                        result.getUser().getPhone(),
                        result.getUser().getDepartment(),
                        result.getResultStatus()
                ))
                .collect(Collectors.toList());
    }

    /* 프론트 지원한 유저 */
    public List<UserListDto> getFrontendUserList(){
        return frontendResumeRepository.findAll().stream()
                .filter(FrontendResume::isApply)
                .map(result -> new UserListDto(
                        result.getUser().getName(),
                        result.getUser().getStudentId(),
                        result.getUser().getPhone(),
                        result.getUser().getDepartment(),
                        result.getResultStatus()
                ))
                .collect(Collectors.toList());
    }

    /* 디자인 지원한 유저 */
    public List<UserListDto> getDesginUserList(){
        return designResumeRepository.findAll().stream()
                .filter(DesignResume::isApply)
                .map(result -> new UserListDto(
                        result.getUser().getName(),
                        result.getUser().getStudentId(),
                        result.getUser().getPhone(),
                        result.getUser().getDepartment(),
                        result.getResultStatus()
                ))
                .collect(Collectors.toList());
    }

}
