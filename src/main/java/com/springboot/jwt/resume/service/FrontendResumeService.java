package com.springboot.jwt.resume.service;

import com.springboot.jwt.resume.dto.FrontendResumeRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface FrontendResumeService {
    void frontendCreateResume(FrontendResumeRequestDto frontendResumeRequestDto);
    void frontendUpdateResume(Long id, FrontendResumeRequestDto frontendResumeRequestDto);
    FrontendResumeRequestDto frontendGetResumeById(Long id);
}