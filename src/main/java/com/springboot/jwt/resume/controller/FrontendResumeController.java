package com.springboot.jwt.resume.controller;

import com.springboot.jwt.resume.dto.BackendResumeRequestDto;
import com.springboot.jwt.resume.dto.DesignResumeRequestDto;
import com.springboot.jwt.resume.dto.FrontendResumeRequestDto;
import com.springboot.jwt.resume.entity.FrontendResume;
import com.springboot.jwt.resume.service.FrontendResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/form/frontend")
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
    public ResponseEntity<FrontendResumeRequestDto> viewResume(@RequestParam Long id) {
        FrontendResumeRequestDto frontendResumeRequestDto = frontendResumeService.frontendGetResumeById(id);
        return ResponseEntity.ok(frontendResumeRequestDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> frontendUpdateResume(
            @PathVariable Long id,
            @RequestBody FrontendResumeRequestDto frontendResumeRequestDto) {
        frontendResumeService.frontendUpdateResume(id, frontendResumeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
