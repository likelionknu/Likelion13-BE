package com.springboot.jwt.resume.service;

import com.springboot.jwt.resume.dto.FrontendResumeRequestDto;

public interface FrontendResumeService {
    void frontendCreateResume(FrontendResumeRequestDto frontendResumeRequestDto);
    void frontendUpdateResume(Long id, FrontendResumeRequestDto frontendResumeRequestDto);
    FrontendResumeRequestDto frontendGetResumeById(Long id);

    FrontendResumeRequestDto updateResumeStatus(Long id, boolean apply);
}