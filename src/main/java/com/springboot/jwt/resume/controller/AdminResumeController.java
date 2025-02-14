package com.springboot.jwt.resume.controller;

import com.springboot.jwt.resume.dto.ResumeRequestDto;
import com.springboot.jwt.resume.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminResumeController {
    private final ResumeService resumeService;
    public AdminResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    /* 관리자 페이지 */

    //상태에 따른 지원서 확인
    @GetMapping("/admin/submit/true")
    public ResponseEntity<List<ResumeRequestDto>> checkApplyTrue() {
        List<ResumeRequestDto> resumes = resumeService.findByApply(true);
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/admin/submit/false")
    public ResponseEntity<List<ResumeRequestDto>> checkApplyFalse() {
        List<ResumeRequestDto> resumes = resumeService.findByApply(false);
        return ResponseEntity.ok(resumes);
    }


    @PostMapping("/admin/userForm/{id}")
    public ResponseEntity<ResumeRequestDto> showUserResume(@PathVariable Long id) {
        ResumeRequestDto resumeRequestDto = resumeService.getResumeById(id);
        return ResponseEntity.ok(resumeRequestDto);
    }

}

