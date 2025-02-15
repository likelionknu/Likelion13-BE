package com.springboot.jwt.resume.controller;

import com.springboot.jwt.resume.dto.BackendResumeRequestDto;
import com.springboot.jwt.resume.entity.BackendResume;
import com.springboot.jwt.resume.service.BackendResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/form/backend")
public class BackendResumeController {
    private final BackendResumeService backendResumeService;

    public BackendResumeController(BackendResumeService backendResumeService) {
        this.backendResumeService = backendResumeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> backendCreateResume(@RequestBody BackendResumeRequestDto backendResumeRequestDto) {
        try {
            backendResumeService.backendCreateResume(backendResumeRequestDto);
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
    public ResponseEntity<BackendResumeRequestDto> viewResume(@RequestParam Long id) {
        BackendResumeRequestDto backendResumeRequestDto = backendResumeService.backendGetResumeById(id);
        return ResponseEntity.ok(backendResumeRequestDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void>backendUpdateResume(@PathVariable Long id, @RequestBody BackendResumeRequestDto backendResumeRequestDto) {
        backendResumeService.backendUpdateResume(id, backendResumeRequestDto);
        return new ResponseEntity<> (HttpStatus.OK);

    }
}
