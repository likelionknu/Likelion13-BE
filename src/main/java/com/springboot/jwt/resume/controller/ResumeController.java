package com.springboot.jwt.resume.controller;

import com.springboot.jwt.resume.dto.ResumeRequestDto;
import com.springboot.jwt.resume.service.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/form")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createResume(@RequestBody ResumeRequestDto resumeRequestDto) {
        resumeService.createResume(resumeRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/view")
    public ResponseEntity<ResumeRequestDto> viewResume(@RequestParam Long id) {
        ResumeRequestDto resumeRequestDto = resumeService.getResumeById(id);
        return ResponseEntity.ok(resumeRequestDto);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updateResume(@PathVariable Long id, @RequestBody ResumeRequestDto resumeRequestDto) {
        resumeService.updateResume(id, resumeRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 상태 변경 (apply = True 이면 제출, 기본값 false)
    @PutMapping("/submit/{id}")
    public ResponseEntity<ResumeRequestDto> submitResume(@PathVariable Long id) {
        ResumeRequestDto updatedResume = resumeService.updateResumeStatus(id, true);
        return ResponseEntity.ok(updatedResume);

    }
}
