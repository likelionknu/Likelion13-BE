package com.springboot.jwt.resume.service;

import com.springboot.jwt.resume.dto.BackendResumeRequestDto;


public interface BackendResumeService {
    void backendCreateResume(BackendResumeRequestDto backendResumeRequestDto);
    void backendUpdateResume(String studentId, BackendResumeRequestDto backendresumeRequestDto);
    BackendResumeRequestDto backendGetResumeById(String studentId);

    BackendResumeRequestDto updateResumeStatus(String studentId, boolean apply);
}