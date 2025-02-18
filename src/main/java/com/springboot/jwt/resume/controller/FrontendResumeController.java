package com.springboot.jwt.resume.controller;

import com.springboot.jwt.resume.dto.FrontendResumeRequestDto;
import com.springboot.jwt.resume.service.FrontendResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/form/frontend")
@CrossOrigin(origins = {"http://localhost:3000", "https://www.likelionknu.com"})
public class FrontendResumeController {
    private final FrontendResumeService frontendResumeService;

    public FrontendResumeController(FrontendResumeService frontendResumeService) {
        this.frontendResumeService = frontendResumeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> frontendCreateResume(@RequestBody FrontendResumeRequestDto frontendResumeRequestDto) {
        try {
            frontendResumeService.frontendCreateResume(frontendResumeRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "지원서가 성공적으로 저장되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "서버 오류가 발생했습니다."));
        }
    }

    @GetMapping("/view")
    public ResponseEntity<FrontendResumeRequestDto> viewResume(@RequestParam String studentId) {
        FrontendResumeRequestDto frontendResumeRequestDto = frontendResumeService.frontendGetResumeById(studentId);
        return ResponseEntity.ok(frontendResumeRequestDto);
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<Void> frontendUpdateResume(
            @RequestParam String studentId,
            @RequestBody FrontendResumeRequestDto frontendResumeRequestDto) {
        frontendResumeService.frontendUpdateResume(studentId, frontendResumeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* 최종 제출 */
    @PutMapping("/frontend/submit/{studentId}")
    public ResponseEntity<FrontendResumeRequestDto> submitResume(@RequestParam String studentId) {
        FrontendResumeRequestDto updatedResume = frontendResumeService.updateResumeStatus(studentId, true);
        return ResponseEntity.ok(updatedResume);
    }
}
