package com.springboot.jwt.resume.service;

import com.springboot.jwt.resume.dto.DesignResumeRequestDto;
import com.springboot.jwt.resume.dto.FrontendResumeRequestDto;

public interface FrontendResumeService {
    void frontendCreateResume(FrontendResumeRequestDto frontendResumeRequestDto);
    void frontendUpdateResume(String studentId, FrontendResumeRequestDto frontendResumeRequestDto);
    FrontendResumeRequestDto frontendGetResumeById(String studentId);

    FrontendResumeRequestDto updateResumeStatus(String studentId, boolean apply);
}