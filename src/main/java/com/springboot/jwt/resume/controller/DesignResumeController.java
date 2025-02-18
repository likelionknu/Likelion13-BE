package com.springboot.jwt.resume.controller;

import com.springboot.jwt.resume.dto.BackendResumeRequestDto;
import com.springboot.jwt.resume.dto.DesignResumeRequestDto;
import com.springboot.jwt.resume.entity.DesignResume;
import com.springboot.jwt.resume.service.DesignResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/form/design")
@CrossOrigin(origins = {"http://localhost:3000", "https://www.likelionknu.com"})
public class DesignResumeController {
    private final DesignResumeService designResumeService;

    public DesignResumeController(DesignResumeService designResumeService) {
        this.designResumeService = designResumeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> designCreateResume(@RequestBody DesignResumeRequestDto designResumeRequestDto) {
        try {
            designResumeService.designCreateResume(designResumeRequestDto);
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
    public ResponseEntity<DesignResumeRequestDto> viewResume(@RequestParam String studentId) {
        DesignResumeRequestDto designResumeRequestDto = designResumeService.designGetResumeById(studentId);
        return ResponseEntity.ok(designResumeRequestDto);
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<Void> designUpdateResume(
            @RequestParam String studentId,
            @RequestBody DesignResumeRequestDto designResumeRequestDto) {
        designResumeService.designUpdateResume(studentId, designResumeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* 최종 제출 */
    @PutMapping("/design/submit/{studentId}")
    public ResponseEntity<DesignResumeRequestDto> submitResume(@RequestParam String studentId) {
        DesignResumeRequestDto updatedResume = designResumeService.updateResumeStatus(studentId, true);
        return ResponseEntity.ok(updatedResume);
    }
}
