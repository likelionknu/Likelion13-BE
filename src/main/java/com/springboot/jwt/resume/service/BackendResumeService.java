package com.springboot.jwt.resume.service;

import com.springboot.jwt.resume.dto.BackendResumeRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface BackendResumeService {
    void backendCreateResume(BackendResumeRequestDto backendResumeRequestDto);
    void backendUpdateResume(Long id, BackendResumeRequestDto backendresumeRequestDto);
    BackendResumeRequestDto backendGetResumeById(Long id);
}